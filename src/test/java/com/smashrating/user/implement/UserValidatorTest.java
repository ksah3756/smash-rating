package com.smashrating.user.implement;

import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.UserRepository;
import com.smashrating.user.UserTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = { // core 모듈의 domain package들을 scan
        "com.smashrating.user.domain"
})
@EnableJpaRepositories(basePackages = { // core 모듈의 infrastructure package들을 scan
        "com.smashrating.user.infrastructure"
})
@Import({UserValidator.class})
class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository userRepository;

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
                "testEmail@gmail.com",
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
                "testUser2",
                "testPassword",
                "testName",
                "testEmail@gmail.com",
                Role.ROLE_USER
        );

        boolean isDuplicate = userValidator.isUsernameDuplicate(user2.getUsername());

        // then
        assertFalse(isDuplicate);
    }
}