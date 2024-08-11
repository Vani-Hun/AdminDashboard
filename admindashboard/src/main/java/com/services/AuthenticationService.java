package com.services;

import com.controller.AuthGoogleController;
import com.dto.AdminDTO;
import com.entity.AdminEntity;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private static final String GOOGLE_TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
    private static final List<String> SCOPES = Arrays.asList(
            GmailScopes.GMAIL_READONLY,
            GmailScopes.GMAIL_SEND,
            GmailScopes.GMAIL_MODIFY
    );
    private final RestTemplate restTemplate;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final GmailService gmailService;
    Jedis jedis;

    public AuthenticationService(GmailService gmailService, PasswordEncoder passwordEncoder, AdminService adminService, RestTemplate restTemplate, Jedis jedis) {
        this.gmailService = gmailService;
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.restTemplate = restTemplate;
        this.jedis = jedis;
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

    public AdminDTO authenticate(AdminDTO adminDTO) {
        Optional<AdminEntity> adminOptional = adminService.getAdminByUsername(adminDTO.getUsername());
        if (adminOptional.isPresent()) {
            AdminEntity admin = adminOptional.get();
            String passwordDTO = adminDTO.getPassword();
            String passwordDAO = admin.getPassword();

            if (passwordEncoder.matches(CharBuffer.wrap(passwordDTO), passwordDAO)) {
                return new AdminDTO(admin.getId(), admin.getUsername(), admin.getName(), admin.getLogo(), null, admin.getPermission(), null);
            }
        }
        throw new RuntimeException("Invalid password");
    }

    public AdminDTO findByLogin(String username) {
        Optional<AdminEntity> adminOptional = adminService.getAdminByUsername(username);
        if (adminOptional.isPresent()) {
            AdminEntity admin = adminOptional.get();
            return new AdminDTO(admin.getId(), admin.getUsername(), admin.getName(), admin.getLogo(), null, admin.getPermission(), null);
        }
        throw new RuntimeException("Invalid login");
    }


    public ResponseEntity<?> authenticateGoogle(Map<String, String> tokenRequest, HttpSession session) {
        try {
            String CLIENT_ID = find("clientId");
            System.out.println("CLIENT_ID: " + CLIENT_ID);
            String CLIENT_SECRET = find("clientSecret");
            String REDIRECT_URI = find("redirectUri");
            ResponseEntity<AuthGoogleController.GoogleUserInfo> response = restTemplate.getForEntity(
                    GOOGLE_TOKEN_INFO_URL + tokenRequest.get("token"),
                    AuthGoogleController.GoogleUserInfo.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                AuthGoogleController.GoogleUserInfo userInfo = response.getBody();

                // Bước 2: Tạo URL xác thực OAuth2
                String authUrl = "https://accounts.google.com/o/oauth2/auth?" +
                        "client_id=" + CLIENT_ID +
                        "&redirect_uri=" + REDIRECT_URI +
                        "&response_type=code" +
                        "&scope=https://www.googleapis.com/auth/gmail.readonly" +
                        "&access_type=offline" +
                        "&prompt=consent" +
                        "&login_hint=" + userInfo.getEmail(); // Thêm email để pre-fill

                // Bước 3: Lưu thông tin người dùng vào session
                session.setAttribute("pendingAuth", true);
                session.setAttribute("userEmail", userInfo.getEmail());

                // Trả về URL xác thực để frontend chuyển hướng người dùng
                return ResponseEntity.ok(Map.of("authUrl", authUrl));
            }
            return ResponseEntity.badRequest().body("Invalid token");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + e.getMessage());
        }
    }

    public ResponseEntity<?> authenticateTopicPubSub(Map<String, String> payload) {
        try {
            save("clientId", payload.get("clientId"));
            save("clientSecret", payload.get("clientSecret"));
            save("redirectUri", payload.get("redirectUri"));
            return ResponseEntity.ok().body("authenticateTopicPubSub processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + e.getMessage());
        }
    }

    public ResponseEntity<?> oauth2Callback(String code, HttpSession session) {
        try {
            String CLIENT_ID = find("clientId");
            String CLIENT_SECRET = find("clientSecret");
            String REDIRECT_URI = find("redirectUri");
            if (session.getAttribute("pendingAuth") == null) {
                return ResponseEntity.badRequest().body("Invalid authentication flow");
            }
            System.out.println("code: " + code);

            GoogleTokenResponse tokenResponse =
                    new GoogleAuthorizationCodeTokenRequest(
                            new NetHttpTransport(),
                            GsonFactory.getDefaultInstance(),
                            "https://oauth2.googleapis.com/token",
                            CLIENT_ID,
                            CLIENT_SECRET,
                            code,
                            REDIRECT_URI)
                            .execute();
            System.out.println("tokenResponse: " + tokenResponse);
            String accessToken = tokenResponse.getAccessToken();
            String refreshToken = tokenResponse.getRefreshToken();
            Long expiresInSeconds = tokenResponse.getExpiresInSeconds();
            System.out.println("refreshToken: " + refreshToken);

            Gmail gmail = gmailService.getGmailService(accessToken, refreshToken, expiresInSeconds);

            gmailService.watchGmail(accessToken, refreshToken, expiresInSeconds);
            String userEmail = gmail.users().getProfile("me").execute().getEmailAddress();
            System.out.println("userEmail: " + userEmail);

            save("accessToken" + userEmail, accessToken);
            if (refreshToken != null) {
                save("refreshToken" + userEmail, refreshToken);
            }
            session.removeAttribute("pendingAuth");

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "email", session.getAttribute("userEmail")
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OAuth2 callback failed: " + e.getMessage());
        }
    }
}