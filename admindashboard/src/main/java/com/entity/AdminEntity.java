package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class AdminEntity extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "name", nullable = true)
    private String name;
    @Column(name = "logo", nullable = true)
    private String logo;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "permission", nullable = false)
    private String permission;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
