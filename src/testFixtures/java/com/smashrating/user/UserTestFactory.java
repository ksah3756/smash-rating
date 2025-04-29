package com.smashrating.user;

import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;

public class UserTestFactory {
    public static User createDefaultUser() {
        return User.create(
                "testUser",
                "testPassword",
                "testName",
                "testNickname",
                "testEmail@test.com",
                Role.ROLE_USER
        );
    }

    public static User createUser1() {
        return User.create(
                "testUser1",
                "testPassword",
                "testName1",
                "testNickname1",
                "testEmail1@test.com",
                Role.ROLE_USER
        );
    }

    public static User createUser2() {
        return User.create(
                "testUser2",
                "testPassword",
                "testName2",
                "testNickname2",
                "testEmail2@test.com",
                Role.ROLE_USER
        );
    }
}
