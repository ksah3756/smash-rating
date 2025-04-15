package com.smashrating.user.application;

import com.smashrating.common.exception.CustomException;
import com.smashrating.user.domain.User;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import com.smashrating.user.event.UserCreatedEvent;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.implement.UserValidator;
import com.smashrating.user.implement.UserWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserValidator userValidator;
    @Mock
    private UserWriter userWriter;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("username 중복이면 예외를 던진다")
    void createMember_throwsException_whenUsernameDuplicate() {
        // given
        UserCreateRequest request = new UserCreateRequest("test", "pw", "name", "email");
        given(userValidator.isUsernameDuplicate("test")).willReturn(true);

        // expect
        assertThatThrownBy(() -> userService.createMember(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(UserErrorCode.USER_DUPLICATE.getMessage());

        then(userWriter).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("회원가입 성공 시 UserWriter와 EventPublisher가 호출된다")
    void createMember_success() {
        // given
        UserCreateRequest request = new UserCreateRequest("test", "pw", "name", "email");
        User mockUser = mock(User.class);
        given(userValidator.isUsernameDuplicate("test")).willReturn(false);
        given(userWriter.createUser("test", "pw", "name", "email")).willReturn(mockUser);
        given(mockUser.getId()).willReturn(1L);
        given(mockUser.getUsername()).willReturn("test");

        ArgumentCaptor<UserCreatedEvent> eventCaptor = ArgumentCaptor.forClass(UserCreatedEvent.class);

        // when
        UserCreateResponse response = userService.createMember(request);

        // then
        assertThat(response.id()).isEqualTo(1L);
        then(userWriter).should().createUser("test", "pw", "name", "email");
        then(eventPublisher).should().publishEvent(eventCaptor.capture());

        UserCreatedEvent publishedEvent = eventCaptor.getValue();
        assertThat(publishedEvent.getUsername()).isEqualTo("test");
    }
}