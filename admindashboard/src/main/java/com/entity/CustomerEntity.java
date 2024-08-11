package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerEntity extends BaseEntity {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "following",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @JsonIgnoreProperties({
            "liked_videos", "user_conversations", "participant_conversations", "following",
            "followers", "videos", "notifications", "comments"
    })
    private List<CustomerEntity> following;

    @Column(nullable = true)
    private String logo;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String bio;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true, columnDefinition = "varchar(255) default 'Everyone'")
    private String message_status;
    @Column(nullable = true, columnDefinition = "boolean default true")
    private boolean video_liked_status;
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"admin_id", "user_id", "participant_id", "messages"})
    private List<ConversationEntity> user_conversations;

    @OneToMany(mappedBy = "participant_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"admin_id", "user_id", "participant_id", "messages"})
    private List<ConversationEntity> participant_conversations;

    @Column(nullable = true)
    private String permission;

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({
            "user_conversations", "participant_conversations", "following",
            "followers", "videos", "notifications", "comments", "liked_videos"
    })
    private List<CustomerEntity> followers;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user", "hashtags", "likers", "comments", "notifications"})
    private List<VideoEntity> videos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user", "video", "interacting_user"})
    private List<NotificationEntity> notifications;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"video", "customer"})
    private List<CommentEntity> comments;

    @ManyToMany(mappedBy = "likers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({
            "user", "hashtags", "likers", "comments", "notifications", "liked_videos"
    })
    private List<VideoEntity> liked_videos;

    // Getters and Setters
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessageStatus() {
        return message_status;
    }

    public void setMessageStatus(String message_status) {
        this.message_status = message_status;
    }

    public boolean getVideoLikedStatus() {
        return video_liked_status;
    }

    public void setVideoLikedStatus(boolean message_status) {
        this.video_liked_status = video_liked_status;
    }

    @JsonIgnore
    public List<ConversationEntity> getUserConversations() {
        return user_conversations;
    }

    @JsonProperty("user_conversations")
    public void setUserConversations(List<ConversationEntity> user_conversations) {
        this.user_conversations = user_conversations;
    }

    @JsonIgnore
    public List<ConversationEntity> getParticipantConversations() {
        return participant_conversations;
    }

    @JsonProperty("participant_conversations")
    public void setParticipantConversations(List<ConversationEntity> participant_conversations) {
        this.participant_conversations = participant_conversations;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<CustomerEntity> getFollowers() {
        return followers;
    }

    @JsonProperty("followers")
    public void setFollowers(List<CustomerEntity> followers) {
        this.followers = followers;
    }

    public List<CustomerEntity> getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(List<CustomerEntity> followers) {
        this.following = following;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    //    @JsonIgnore
    public List<VideoEntity> getVideos() {
        return videos;
    }

    @JsonProperty("videos")
    public void setVideos(List<VideoEntity> videos) {
        this.videos = videos;
    }

    //    @JsonIgnore
    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    @JsonProperty("notifications")
    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    @JsonIgnore
    public List<CommentEntity> getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    //    @JsonIgnore
    public List<VideoEntity> getLikedVideos() {
        return liked_videos;
    }

    @JsonProperty("liked_videos")
    public void setLikedVideos(List<VideoEntity> liked_videos) {
        this.liked_videos = liked_videos;
    }
}
