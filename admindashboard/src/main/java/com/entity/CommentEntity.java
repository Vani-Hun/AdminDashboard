package com.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "video", nullable = false)
    private VideoEntity video;

    @ManyToOne
    @JoinColumn(name = "customer", nullable = false)
    private CustomerEntity customer;

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public VideoEntity getVideo() {
        return video;
    }

    public void setVideo(VideoEntity video) {
        this.video = video;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
