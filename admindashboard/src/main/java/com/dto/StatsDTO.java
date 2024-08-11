package com.dto;

public class StatsDTO {
    private Object date;
    private long count;
    private long total_comments;
    private long total_likes;
    private long views;

    public StatsDTO(Object date, long count, long total_comments, long total_likes, long views) {
        this.date = date;
        this.count = count;
        this.total_comments = total_comments;
        this.total_likes = total_likes;
        this.views = views;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(long total_comments) {
        this.total_comments = total_comments;
    }

    public long getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(long total_likes) {
        this.total_likes = total_likes;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
