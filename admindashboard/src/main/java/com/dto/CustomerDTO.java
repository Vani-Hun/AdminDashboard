package com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomerDTO {

    private String id;
    private String logo;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String bio;
    private String password;
    private String messageStatus;
    private String permission;
    private int likes;
    private List<VideoDTO> videos; // Assuming VideoDTO is defined similarly
    private List<NotificationDTO> notifications; // Assuming NotificationDTO is defined similarly
    private List<CommentDTO> comments; // Assuming CommentDTO is defined similarly
    private List<VideoDTO> likedVideos; // Assuming VideoDTO is defined similarly

    public CustomerDTO(String id, String logo, String name, String username, String email, String phone,
                       String bio, String password, String messageStatus, String permission, int likes,
                       List<VideoDTO> videos, List<NotificationDTO> notifications, List<CommentDTO> comments,
                       List<VideoDTO> likedVideos) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
        this.password = password;
        this.messageStatus = messageStatus;
        this.permission = permission;
        this.likes = likes;
        this.videos = videos;
        this.notifications = notifications;
        this.comments = comments;
        this.likedVideos = likedVideos;
    }

    // Getters and setters
    // Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Logo
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Bio
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Message Status
    @JsonProperty("message_status")
    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    // Permission
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    // Likes
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    // Videos
    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    // Notifications
    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    // Comments
    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    // Liked Videos
    @JsonProperty("liked_videos")
    public List<VideoDTO> getLikedVideos() {
        return likedVideos;
    }

    public void setLikedVideos(List<VideoDTO> likedVideos) {
        this.likedVideos = likedVideos;
    }
}
