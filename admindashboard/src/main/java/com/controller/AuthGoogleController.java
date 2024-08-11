package com.controller;

import com.services.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SessionAttributes("accessToken")
@RestController
@RequestMapping("/api/auth")
public class AuthGoogleController {
    AuthenticationService authenticationService;

    public AuthGoogleController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticateGoogle(@RequestBody Map<String, String> tokenRequest, HttpSession session) {
        System.out.println("tokenRequest: " + tokenRequest);
        return authenticationService.authenticateGoogle(tokenRequest, session);
    }

    @PostMapping("/topic")
    public ResponseEntity<?> authenticateTopicPubSub(@RequestBody Map<String, String> payload) {
        System.out.println("payload: " + payload);
        return authenticationService.authenticateTopicPubSub(payload);
    }

    @GetMapping("/oauth2callback")
    public ResponseEntity<?> oauth2Callback(@RequestParam("code") String code, HttpSession session) {

        return authenticationService.oauth2Callback(code, session);
    }

    public static class GoogleUserInfo {
        private String sub;
        private String email;
        private String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSub() {
            return email;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public String getName() {
            return email;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

