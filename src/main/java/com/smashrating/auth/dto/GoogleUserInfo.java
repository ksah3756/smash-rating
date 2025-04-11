package com.smashrating.auth.dto;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attribute;

    public GoogleUserInfo(Map<String, Object> attribute) {
        this.attribute = attribute;

    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
