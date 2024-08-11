package com.dto;

public class ErrorDTO {
    private int statusCode;
    private String message;
    private String details;

    public ErrorDTO(int statusCode, String message, String details) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    // Getters and setters

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}