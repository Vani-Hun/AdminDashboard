package com.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hashtag")
public class HashtagEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "int default 0")
    private int usage;

    @ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VideoEntity> videos;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public List<VideoEntity> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoEntity> videos) {
        this.videos = videos;
    }
}
