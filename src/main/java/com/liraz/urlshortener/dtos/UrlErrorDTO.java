package com.liraz.urlshortener.dtos;

public class UrlErrorDTO {
    private String status;
    private String message;

    public String getStatus() { return status; }

    public String getMessage() { return message; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String error) {
        this.message = error;
    }

    @Override
    public String toString() {
        return "UrlErrorDTO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
