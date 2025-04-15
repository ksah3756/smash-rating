package com.smashrating.user;

import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;

public class UserTestFactory {
    public static User createDefaultUser() {
        return User.create(
                "testUser",
                "testPassword",
                "testName",
                "testEmail@test.com",
                Role.ROLE_USER
        );
    }
}
