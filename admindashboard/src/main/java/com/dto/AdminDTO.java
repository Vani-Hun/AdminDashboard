package com.dto;

public class AdminDTO {
    private String id;
    private String username;
    private String name;
    private String logo;
    private String password;
    private String permission;
    private String token;

    public AdminDTO() {
    }

    public AdminDTO(String id, String username, String name, String logo, String password, String permission, String token) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.logo = logo;
        this.password = password;
        this.permission = permission;
        this.token = token;
    }

    public AdminDTO(String username, String name, String logo, String permission) {
        this.username = username;
        this.name = name;
        this.logo = logo;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
