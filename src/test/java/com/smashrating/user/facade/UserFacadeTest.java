package com.smashrating.user.facade;

import com.smashrating.common.annotation.IntegrationTest;
import com.smashrating.common.exception.CustomException;
import com.smashrating.user.UserCreateRequestTestFactory;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.domain.User;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IntegrationTest
class UserFacadeTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFacade userFacade;

    @Test
    @DisplayName("username 중복이면 예외를 던진다")
    void createMember_throwsException_whenUsernameDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);
        UserCreateRequest request = UserCreateRequest.builder()
                .username("testUser") // username 중복
                .password("testPassword")
                .realName("testName")
                .nickname("uniqueNickname")
                .email("unique@test.com")
                .build();

        // expect
        assertThatThrownBy(() -> userFacade.createMember(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(UserErrorCode.USER_USERNAME_DUPLICATE.getMessage());
    }

    @Test
    @DisplayName("email 중복이면 예외를 던진다")
    void createMember_throwsException_whenEmailDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        UserCreateRequest request = UserCreateRequest.builder()
                .username("uniqueUser")
                .password("testPassword")
                .realName("testName")
                .nickname("uniqueNickname")
                .email("testEmail@test.com") // 이메일 중복
                .build();

        // expect
        assertThatThrownBy(() -> userFacade.createMember(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(UserErrorCode.USER_EMAIL_DUPLICATE.getMessage());
    }

    @Test
    @DisplayName("nickname 중복이면 예외를 던진다")
    void createMember_throwsException_whenNicknameDuplicate() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        UserCreateRequest request = UserCreateRequest.builder()
                .username("uniqueUser")
                .password("testPassword")
                .realName("testName")
                .nickname("testNickname") // 닉네임 중복
                .email("unique@test.com")
                .build();

        // expect
        assertThatThrownBy(() -> userFacade.createMember(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(UserErrorCode.USER_NAME_DUPLICATE.getMessage());
    }

    @Test
    @DisplayName("회원가입 성공")
    void createMember_success() {
        // given
        UserCreateRequest request = UserCreateRequestTestFactory.createDefaultRequest();

        // when
        UserCreateResponse response = userFacade.createMember(request);

        // then
        assertThat(response.id()).isNotNull();
    }
}