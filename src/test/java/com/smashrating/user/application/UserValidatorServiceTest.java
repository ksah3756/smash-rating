package com.smashrating.user.application;

import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.FakeUserRepository;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorServiceTest {

    private UserRepository userRepository;
    private UserValidatorService userValidator;

   @BeforeEach
    void setUp() {
       userRepository = new FakeUserRepository();
       userValidator = new UserValidatorService(userRepository);
   }

    @Test
    @DisplayName("유저 이름이 중복될 경우 true를 반환한다.")
    void isUsernameDuplicate_duplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();

        userRepository.save(user);

        // when
        User user2 = User.create(
                "testUser",
                "testPassword",
                "testName",
                "uniqueNickname",
                "unique@gmail.com",
                Role.ROLE_USER
        );

        boolean isDuplicate = userValidator.isUsernameDuplicate(user2.getUsername());

        // then
        assertTrue(isDuplicate);
    }

    @Test
    @DisplayName("유저 이름이 중복되지 않을 경우 false를 반환한다.")
    void isUsernameDuplicate_notDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();

        userRepository.save(user);

        // when
        User user2 = User.create(
                "uniqueUsername",
                "testPassword",
                "testName",
                "testNickname",
                "testEmail@gmail.com",
                Role.ROLE_USER
        );

        boolean isDuplicate = userValidator.isUsernameDuplicate(user2.getUsername());

        // then
        assertFalse(isDuplicate);
    }

    @Test
    @DisplayName("이메일이 중복될 경우 true를 반환한다.")
    void isEmailDuplicate_duplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        // when
        User user2 = User.create(
                "uniqueUsername",
                "testPassword",
                "testName",
                "testNickname",
                user.getEmail(),
                Role.ROLE_USER
        );
        boolean isDuplicate = userValidator.isEmailDuplicate(user2.getEmail());

        // then
        assertTrue(isDuplicate);
    }

    @Test
    @DisplayName("이메일이 중복되지 않을 경우 false를 반환한다.")
    void isEmailDuplicate_notDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        // when
        User user2 = User.create(
                "uniqueUsername",
                "testPassword",
                "testName",
                "testNickname",
                "unique@gmail.com",
                Role.ROLE_USER
        );
        boolean isDuplicate = userValidator.isEmailDuplicate(user2.getEmail());

        // then
        assertFalse(isDuplicate);
    }

    @Test
    @DisplayName("닉네임이 중복될 경우 true를 반환한다.")
    void isNicknameDuplicate_duplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        // when
        User user2 = User.create(
                "uniqueUsername",
                "testPassword",
                "testName",
                "testNickname",
                "unique@gmail.com",
                Role.ROLE_USER
        );
        boolean isDuplicate = userValidator.isNicknameDuplicate(user2.getNickname());

        // then
        assertTrue(isDuplicate);
    }

    @Test
    @DisplayName("닉네임이 중복되지 않을 경우 false를 반환한다.")
    void isNicknameDuplicate_notDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        // when
        User user2 = User.create(
                "uniqueUsername",
                "testPassword",
                "testName",
                "uniqueNickname",
                "unique@gmail.com",
                Role.ROLE_USER
        );
        boolean isDuplicate = userValidator.isNicknameDuplicate(user2.getNickname());

        // then
        assertFalse(isDuplicate);
    }
}