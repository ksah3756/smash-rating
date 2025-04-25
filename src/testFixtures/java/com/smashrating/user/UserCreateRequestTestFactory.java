package com.smashrating.user;

import com.smashrating.user.dto.UserCreateRequest;

public class UserCreateRequestTestFactory {
    public static UserCreateRequest createDefaultRequest() {
        return UserCreateRequest.builder()
                .username("testUser")
                .password("testPassword")
                .realName("testName")
                .nickname("testNickname")
                .email("testEmail@test.com")
                .build();
    }
}
