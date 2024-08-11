package com.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send-message")
    public void sendMessageToClient() {
        String destination = "/topic/public"; // Địa chỉ đích của tin nhắn
        String message = "Hello from server!"; // Nội dung tin nhắn

        messagingTemplate.convertAndSend(destination, message);
    }
}
