package com.liraz.urlshortener.dtos;

public class UrlResponseDTO {
    private String originalUrl;
    private String shortUrl;
    private String expiresOn;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
    }

    @Override
    public String toString() {
        return "UrlResponseDTO{" +
                "originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", expiresOn='" + expiresOn + '\'' +
                '}';
    }
}
