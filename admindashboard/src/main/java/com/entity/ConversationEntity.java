package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "conversation")
public class ConversationEntity extends BaseEntity {

    @Column(name = "count", nullable = false)
    private final int count = 0;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"liked_videos", "user_conversations", "participant_conversations", "following", "followers", "videos", "notifications", "comments"})
    private CustomerEntity user_id;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    @JsonIgnoreProperties({"liked_videos", "user_conversations", "participant_conversations", "following", "followers", "videos", "notifications", "comments"})
    private CustomerEntity participant_id;

    @OneToMany(mappedBy = "conversation_id", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"conversation_id"})
    private List<MessageEntity> messages;

    // Getters and setters
    public int getCount() {
        return count;
    }

    @JsonProperty("admin_id")
    public AdminEntity getAdmin() {
        return admin_id;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin_id = admin_id;
    }

    @JsonProperty("user_id")
    public CustomerEntity getUser() {
        return user_id;
    }

    public void setUser(CustomerEntity user_id) {
        this.user_id = user_id;
    }

    @JsonProperty("participant_id")
    public CustomerEntity getParticipant() {
        return participant_id;
    }

    public void setParticipant(CustomerEntity participant_id) {
        this.participant_id = participant_id;
    }

    @JsonProperty("messages")
    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
}
