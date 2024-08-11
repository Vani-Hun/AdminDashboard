//package com.config;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.gax.core.FixedCredentialsProvider;
//import com.google.api.services.gmail.Gmail;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
//import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
//import com.google.cloud.pubsub.v1.TopicAdminClient;
//import com.google.cloud.pubsub.v1.TopicAdminSettings;
//import com.google.pubsub.v1.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class GmailConfig {
//
//    private static final String APPLICATION_NAME = "Gmail API Java";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/tokens";
//    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.readonly");
//    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";
//    private static final String SERVICE_ACCOUNT_KEY_FILE_PATH = "src/main/resources/service-account-key.json";
//    @Value("${gcp.project-id}")
//    private String projectId;
//
//    @Value("${gcp.pubsub.topic-id}")
//    private String topicId;
//
//    @Value("${gcp.pubsub.subscription-id}")
//    private String subscriptionId;
//
//
////    @Bean
////    public Gmail gmail() throws IOException, GeneralSecurityException {
////        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
////        Credential credential = getCredentials(HTTP_TRANSPORT);
////        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
////                .setApplicationName(APPLICATION_NAME)
////                .build();
////    }
////
////    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
////        FileInputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
////        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
////        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
////                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
////                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
////                .setAccessType("offline")
////                .build();
////
////        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
////        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
////    }
//
//    @Bean
//    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws IOException, GeneralSecurityException {
//        FileInputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        return new GoogleAuthorizationCodeFlow.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                clientSecrets,
//                SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
//                .setAccessType("offline")
//                .build();
//    }
//
//    @Bean
//    public Gmail gmail(GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow) throws IOException, GeneralSecurityException {
//        return new Gmail.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                GsonFactory.getDefaultInstance(),
//                googleAuthorizationCodeFlow.loadCredential("user"))
//                .setApplicationName("Gmail API Java")
//                .build();
//    }
//
//    //    @Bean
//    public Subscription createSubscription() throws IOException {
//        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_FILE_PATH))
//                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));
//        System.out.println("credentials:" + credentials);
//
//        ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
//        System.out.println("topicName:" + topicName);
//
//        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
//        System.out.println("subscriptionName:" + subscriptionName);
//
//        // Create Topic if it doesn't exist
//        TopicAdminSettings topicAdminSettings = TopicAdminSettings.newBuilder()
//                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
//                .build();
//        System.out.println("topicAdminSettings:" + topicAdminSettings);
//
//        try (TopicAdminClient topicAdminClient = TopicAdminClient.create(topicAdminSettings)) {
//            Topic topic = topicAdminClient.getTopic(topicName.toString());
//            System.out.println("topic:" + topic);
//
//            if (topic == null) {
//                System.out.println("topic == null:");
//
//                topicAdminClient.createTopic(topicName);
//            }
//        }
//
//        PushConfig pushConfig = PushConfig.newBuilder()
//                .setPushEndpoint("/webhook/gmail")
//                .build();
//        System.out.println("pushConfig:" + pushConfig);
//
//        // Create Subscription
//        SubscriptionAdminSettings subscriptionAdminSettings = SubscriptionAdminSettings.newBuilder()
//                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
//                .build();
//        System.out.println("subscriptionAdminSettings:" + subscriptionAdminSettings);
//
//        try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(subscriptionAdminSettings)) {
//            Subscription subscription = subscriptionAdminClient.getSubscription(subscriptionName.toString());
//            System.out.println("subscription:" + subscription);
//
//            if (subscription == null) {
//                System.out.println("subscription == null:");
//
//                subscriptionAdminClient.createSubscription(
//                        subscriptionName,
//                        topicName,
//                        pushConfig,
//                        60 // ackDeadlineSeconds
//                );
//            }
//        }
//
//        return Subscription.newBuilder().setName(subscriptionName.toString()).build();
//    }
//}
//
