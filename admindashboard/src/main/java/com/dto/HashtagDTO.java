package com.dto;

import java.util.List;

public class HashtagDTO {
    private String id;
    private String name;
    private int usage;
    private List<VideoDTO> videos;

    public HashtagDTO(String id, String name, Integer usage) {
        this.id = id;
        this.name = name;
        this.usage = usage;
    }

    public HashtagDTO() {

    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void SetId(String name) {
        this.id = id;
    }

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

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }
}
