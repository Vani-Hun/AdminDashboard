package com.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebhookService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void doWebhook(String destination, Map<String, String> messageClient) {
        messagingTemplate.convertAndSend(destination, messageClient);
    }

    public void doErrWebhook(String destination, String messageClient) {
        Map<String, String> message = new HashMap<>();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDate = now.format(formatter);
        message.put("created_at", formattedDate);
        message.put("caption", messageClient);
        message.put("from", "Err Server");
        messagingTemplate.convertAndSend(destination, message);
    }

}
