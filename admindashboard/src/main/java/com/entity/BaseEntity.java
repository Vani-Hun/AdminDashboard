package com.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date created_at;
    @Column(name = "updated_at", nullable = false, columnDefinition = "VARCHAR(36)")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updated_at;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @PrePersist
    protected void onCreate() {
        created_at = new Date();
        updated_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @JsonProperty("created_at")
    public Date getCreated_at() {
        return created_at;
    }

    private void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @JsonProperty("updated_at")
    public Date getUpdated_at() {
        return updated_at;
    }

    private void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
