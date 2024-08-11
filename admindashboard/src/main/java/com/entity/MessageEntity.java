package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "message")
public class MessageEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonIgnoreProperties({"admin_id", "user_id", "participant_id", "messages"})
    private ConversationEntity conversation_id;

    @Column(nullable = false)
    private String user_id;

    @Column
    private String text;

    @Column
    private String status;

    @JsonProperty("conversation_id")
    public ConversationEntity getConversation() {
        return conversation_id;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation_id = conversation_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
