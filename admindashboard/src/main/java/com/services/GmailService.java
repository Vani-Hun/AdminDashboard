package com.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GmailService {

    private static final String APPLICATION_NAME = "Gmail API Java";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/tokens";
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";
    private static final String SERVICE_ACCOUNT_KEY_FILE_PATH = "src/main/resources/service-account-key.json";
    private final Jedis jedis;
    private final SimpMessagingTemplate messagingTemplate;
    String accessToken;
    String refreshToken;
    private String lastHistoryId;
    @Value("${gcp.pubsub.topic}")
    private String topicName;

    public GmailService(Jedis jedis, SimpMessagingTemplate messagingTemplate) {
        this.jedis = jedis;
        this.messagingTemplate = messagingTemplate;
    }

    public void save(String key, String value) {
        jedis.set(key, value);
    }

    public String find(String key) {
        return jedis.get(key);
    }

    public void delete(String key) {
        jedis.del(key);
    }

    @Cacheable(value = "lastHistoryId")
    public String getLastHistoryId() {
        return lastHistoryId;
    }

    @CachePut(value = "lastHistoryId")
    public void setLastHistoryId(String id) {
        this.lastHistoryId = id;
    }


    protected Gmail getGmailService(String accessToken, String refreshToken, Long expiresInSeconds) throws GeneralSecurityException, IOException {
        String CLIENT_ID = find("clientId");
        String CLIENT_SECRET = find("clientSecret");

        AccessToken token;
        if (expiresInSeconds != null) {
            Date expirationTime = new Date(System.currentTimeMillis() + expiresInSeconds * 1000);
            token = new AccessToken(accessToken, expirationTime);
        } else {
            // Nếu expiresInSeconds là null, tạo AccessToken không có thời gian hết hạn
            token = new AccessToken(accessToken, null);
            System.out.println("Warning: expiresInSeconds is null. Created AccessToken without expiration time.");
        }

        GoogleCredentials credentials;
        if (refreshToken != null) {
            credentials = UserCredentials.newBuilder()
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setAccessToken(token)
                    .setRefreshToken(refreshToken)
                    .build();
            System.out.println("Created credentials with refresh token.");
        } else {
            credentials = GoogleCredentials.create(token);
            System.out.println("Warning: No refresh token available. Created credentials without refresh token.");
        }

        credentials = credentials.createScoped(Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly"));

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Your Application Name")
                .build();
    }

    private boolean isAccessTokenExpired(AccessToken token) {
        Date expirationTime = token.getExpirationTime();
        System.out.println("expirationTime: " + expirationTime);

        return expirationTime != null && expirationTime.before(new Date());
    }

    private String refreshAccessToken(String refreshToken) throws IOException {
        GoogleTokenResponse tokenResponse = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                refreshToken,
                find("clientId"),
                find("clientSecret"))
                .execute();
        System.out.println("tokenResponse: " + tokenResponse);

        return tokenResponse.getAccessToken();
    }

    public void watchGmail(String accessToken, String refreshToken, Long expiresInSeconds) throws IOException, GeneralSecurityException {
        Gmail gmail = getGmailService(accessToken, refreshToken, expiresInSeconds);
        WatchRequest watchRequest = new WatchRequest()
                .setLabelIds(Collections.singletonList("INBOX"))
                .setTopicName(topicName);
        WatchResponse watchResponse = gmail.users().watch("me", watchRequest).execute();
        System.out.println("Watch response: " + watchResponse.toPrettyString());
    }

//    public List<Label> listLabels(String accessToken) throws IOException, GeneralSecurityException {
//        Gmail gmail = getGmailService(accessToken, "null", null, "null");
//        ListLabelsResponse listResponse = gmail.users().labels().list("me").execute();
//        List<Label> labels = listResponse.getLabels();
//        for (Label label : labels) {
//            System.out.println("Label name: " + label.getName());
//        }
//        return labels;
//    }

    public Map<String, String> getEmailDetails(BigInteger historyId, String emailAddress) throws IOException, GeneralSecurityException {
        try {
            System.out.println("historyId: " + historyId);
            System.out.println("emailAddress: " + emailAddress);

            String accessToken = find("accessToken" + emailAddress);
            System.out.println("accessToken: " + accessToken);

            String refreshToken = find("refreshToken" + emailAddress);
            System.out.println("refreshToken: " + refreshToken);

            System.out.println("accessToken: " + accessToken);
            Gmail gmail = getGmailService(accessToken, refreshToken, null);
            System.out.println("gmail: " + gmail);

            Object lastHistoryId = find("lastHistoryId" + emailAddress);
            if (lastHistoryId == null) {
                save("lastHistoryId" + emailAddress, historyId.toString());
            } else {
                ListHistoryResponse historyResponse = gmail.users().history().list("me")
                        .setStartHistoryId(new BigInteger(lastHistoryId.toString()))
                        .execute();
                save("lastHistoryId" + emailAddress, historyId.toString());
                List<History> historyEvents = historyResponse.getHistory();
                if (historyEvents != null) {
                    for (History history : historyEvents) {
                        List<Message> messages = history.getMessages();
                        if (messages != null) {
                            for (Message message : messages) {
                                String messageId = message.getId();
                                Message messageEvents = gmail.users().messages().get("me", messageId).execute();
                                String messageRaw = messageEvents.getSnippet();

                                Map<String, String> messageClient = new HashMap<>();
                                long timestamp = messageEvents.getInternalDate();
                                String createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date(timestamp));
                                messageClient.put("created_at", createdAt);
                                String from = "";
                                String avatarAddress = "";
                                List<MessagePartHeader> headers = messageEvents.getPayload().getHeaders();
                                for (MessagePartHeader header : headers) {
                                    if (header.getName().equals("From")) {
                                        from = header.getValue();
                                        avatarAddress = extractEmail(from);
                                        break;
                                    }
                                }
                                messageClient.put("from", from);
                                messageClient.put("to", emailAddress);
                                String avatarUrl = getGravatarUrl(avatarAddress);
                                messageClient.put("thumbnail", avatarUrl);
                                messageClient.put("caption", messageRaw);
                                System.out.println("messageClient: " + messageClient);
                                return messageClient;
                            }
                        }
                    }
                }
            }
        } catch (IOException | GeneralSecurityException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error processing email details", e);
        }
        return null;
    }

    private String extractEmail(String from) {
        int start = from.indexOf("<");
        int end = from.indexOf(">");
        if (start >= 0 && end > start) {
            return from.substring(start + 1, end);
        }
        return from;
    }

    private String getGravatarUrl(String email) throws Exception {
        String hash = md5Hex(email.trim().toLowerCase());
        return "https://www.gravatar.com/avatar/" + hash + "?d=identicon";
    }

    private String md5Hex(String message) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Không thể tạo hash MD5", ex);
        }
    }

}
