package com.dto;

import java.util.List;

public class VideoDTO {

    private String user;
    private List<CustomerDTO> likers;
    private List<CommentDTO> comments;
    private List<NotificationDTO> notifications;
    private List<HashtagDTO> hashtags;
    private String id;
    private String name;
    private String video;
    private int views;
    private int likes;
    private int shareCount;
    private String caption;
    private String who;
    private String thumbnail;
    private boolean allowComment;

    public VideoDTO(
            String id,
            String user,
            String name,
            String video,
            int views,
            int likes,
            int shareCount,
            String caption,
            String who,
            String thumbnail,
            boolean allowComment,
            List<CustomerDTO> likers,
            List<CommentDTO> comments,
            List<NotificationDTO> notifications,
            List<HashtagDTO> hashtags
    ) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.video = video;
        this.views = views;
        this.likes = likes;
        this.shareCount = shareCount;
        this.caption = caption;
        this.who = who;
        this.thumbnail = thumbnail;
        this.allowComment = allowComment;
        this.likers = likers;
        this.comments = comments;
        this.notifications = notifications;
        this.hashtags = hashtags;
    }

    public VideoDTO() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isAllowComment() {
        return allowComment;
    }

    public void setAllowComment(boolean allowComment) {
        this.allowComment = allowComment;
    }

    public List<CustomerDTO> getLikers() {
        return likers;
    }

    public void setLikers(List<CustomerDTO> likers) {
        this.likers = likers;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    public List<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }
}
