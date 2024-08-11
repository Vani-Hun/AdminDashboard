package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.services.GmailService;
import com.services.PubSubService;
import com.services.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SessionAttributes("accessToken")
@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private final WebhookService webhookService;
    private final GmailService gmailService;
    private final PubSubService pubSubService;

    public WebhookController(WebhookService webhookService, GmailService gmailService, PubSubService pubSubService) {
        this.webhookService = webhookService;
        this.gmailService = gmailService;
        this.pubSubService = pubSubService;

    }

    @PostMapping("/video")
    public ResponseEntity<?> doMSG(@RequestBody Object data) {
        String destination = "/topic/video";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> dataMap = objectMapper.convertValue(data, HashMap.class);
            webhookService.doWebhook(destination, dataMap);
            return ResponseEntity.ok().body("Webhook processed successfully");
        } catch (JsonParseException | IllegalStateException e) {
            throw new IllegalArgumentException("Invalid JSON payload", e);
        } catch (Exception e) {
            webhookService.doErrWebhook(destination, e.toString());
            throw new RuntimeException("Error processing webhook", e);
        }
    }

    @PostMapping("/gmail")
    public ResponseEntity<?> handleGmailWebhook(@RequestBody String payload) {
        String destination = "/topic/email";
        try {
            JsonObject json = JsonParser.parseString(payload).getAsJsonObject();
            String encodedData = json.getAsJsonObject("message").get("data").getAsString();
            String decodedData = new String(Base64.getDecoder().decode(encodedData));
            JsonObject data = JsonParser.parseString(decodedData).getAsJsonObject();
            System.out.println("data: " + data);
            BigInteger historyId = data.get("historyId").getAsBigInteger();
            String emailAddress = data.get("emailAddress").getAsString();
            Map<String, String> messageClient = gmailService.getEmailDetails(historyId, emailAddress);
            if (messageClient != null) {
                webhookService.doWebhook(destination, messageClient);
                return ResponseEntity.ok().body("Webhook processed successfully");
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (JsonParseException | IllegalStateException e) {
            throw new IllegalArgumentException("Invalid JSON payload", e);
        } catch (Exception e) {
            webhookService.doErrWebhook(destination, "Error processing webhook");
            throw new RuntimeException("Error processing webhook", e);
        }
    }

}
