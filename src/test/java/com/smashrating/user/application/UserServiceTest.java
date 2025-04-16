package com.smashrating.user.application;

import com.smashrating.common.exception.CustomException;
import com.smashrating.user.UserCreateRequestTestFactory;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.domain.User;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.implement.UserValidator;
import com.smashrating.user.implement.UserWriter;
import com.smashrating.user.infrastructure.FakeApplicationEventPublisher;
import com.smashrating.user.infrastructure.FakePasswordEncoder;
import com.smashrating.user.infrastructure.FakeUserRepository;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserServiceTest {

    private UserRepository userRepository;
    private UserValidator userValidator;
    private UserWriter userWriter;
    private ApplicationEventPublisher eventPublisher;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        userValidator = new UserValidator(userRepository);
        userWriter = new UserWriter(userRepository, new FakePasswordEncoder());
        eventPublisher = new FakeApplicationEventPublisher();
        userService = new UserService(userWriter, userValidator, eventPublisher);
    }


    @Test
    @DisplayName("username 중복이면 예외를 던진다")
    void createMember_throwsException_whenUsernameDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);
        UserCreateRequest request = UserCreateRequestTestFactory.createDefaultRequest();

        // expect
        assertThatThrownBy(() -> userService.createMember(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(UserErrorCode.USER_USERNAME_DUPLICATE.getMessage());
    }

    @Test
    @DisplayName("회원가입 성공")
    void createMember_success() {
        // given
        UserCreateRequest request = UserCreateRequestTestFactory.createDefaultRequest();

        // when
        UserCreateResponse response = userService.createMember(request);

        // then
        assertThat(response.id()).isEqualTo(1L);
    }
}