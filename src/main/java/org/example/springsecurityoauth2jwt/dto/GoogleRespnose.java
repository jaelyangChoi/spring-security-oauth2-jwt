package org.example.springsecurityoauth2jwt.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleRespnose implements OAuth2Response {
    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }
}
