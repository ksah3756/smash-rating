package com.smashrating.auth.dto;

public interface OAuth2UserInfo {
    String getProvider(); // e.g., "kakao", "naver", "google"
    String getProviderId(); // e.g., "1234567890"
    String getEmail();
    String getName(); // e.g., "John Doe"
}
