package com.dto;

import com.constants.NotificationType;

public class NotificationDTO {

    private String userId;
    private String interactingUserId;
    private String videoId;
    private String message;
    private boolean status;
    private NotificationType type;

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInteractingUserId() {
        return interactingUserId;
    }

    public void setInteractingUserId(String interactingUserId) {
        this.interactingUserId = interactingUserId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
