package com.entity;

import com.constants.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user")
    @JsonIgnoreProperties({"liked_videos", "user_conversations", "participant_conversations", "following", "followers", "videos", "notifications", "comments"})
    private CustomerEntity user;

    @ManyToOne
    @JoinColumn(name = "interacting_user")
    @JsonIgnoreProperties({"liked_videos", "user_conversations", "participant_conversations", "following", "followers", "videos", "notifications", "comments"})
    private CustomerEntity interacting_user;

    @ManyToOne
    @JoinColumn(name = "video")
    @JsonIgnoreProperties({"user", "hashtags", "likers", "comments", "notifications"})
    private VideoEntity video;

    @Column(columnDefinition = "text")
    private String message;

    @Column
    private boolean status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Likes', 'Comments', 'Mention and tags', 'Followers')")
    private NotificationType type;

    @JsonProperty("user")
    public CustomerEntity getUser() {
        return user;
    }

    public void setUser(CustomerEntity user) {
        this.user = user;
    }

    public CustomerEntity getInteractingUser() {
        return interacting_user;
    }

    @JsonProperty("interacting_user")
    public void setInteractingUser(CustomerEntity interacting_user) {
        this.interacting_user = interacting_user;
    }

    @JsonProperty("video")
    public VideoEntity getVideo() {
        return video;
    }

    public void setVideo(VideoEntity video) {
        this.video = video;
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
