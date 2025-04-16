package com.smashrating.user;

import com.smashrating.user.dto.UserCreateRequest;

public class UserCreateRequestTestFactory {
    public static UserCreateRequest createDefaultRequest() {
        return UserCreateRequest.builder()
                .username("testuser")
                .password("testpass")
                .realName("testName")
                .nickname("testNickname")
                .email("testEmail@gmail.com")
                .build();
    }
}
