package org.example.springsecurityoauth2jwt.dto;

public interface OAuth2Response {
    public String getProvider();

    public String getProviderId();

    public String getName();

    public String getEmail();
}
