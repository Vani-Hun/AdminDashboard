package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user")
    @JsonIgnoreProperties({"liked_videos", "user_conversations", "participant_conversations", "following", "followers", "videos", "notifications", "comments"})
    private CustomerEntity user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String video;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    private int share_count;

    @Column(nullable = false)
    private String caption;

    @Column(nullable = false)
    private String who;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private boolean allow_comment;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "video_like",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @JsonIgnoreProperties({"liked_videos", "user_conversations", "participant_conversations", "following", "followers", "videos", "notifications", "comments"})
    private List<CustomerEntity> likers;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "video", "interacting_user"})
    private List<NotificationEntity> notifications;

    @ManyToMany
    @JoinTable(
            name = "video_hashtag",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @JsonIgnoreProperties({"videos"})
    private List<HashtagEntity> hashtags;

    @JsonProperty("user")
    public CustomerEntity getUser() {
        return user;
    }

    public void setUser(CustomerEntity user) {
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
        return share_count;
    }

    public void setShareCount(int share_count) {
        this.share_count = share_count;
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
        return allow_comment;
    }

    public void setAllowComment(boolean allow_comment) {
        this.allow_comment = allow_comment;
    }

    @JsonProperty("likers")
    public List<CustomerEntity> getLikers() {
        return likers;
    }

    public void setLikers(List<CustomerEntity> likers) {
        this.likers = likers;
    }

    @JsonProperty("comments")
    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @JsonProperty("notifications")
    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    @JsonProperty("hashtags")
    public List<HashtagEntity> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashtagEntity> hashtags) {
        this.hashtags = hashtags;
    }
}
