package com.smashrating.auth.application;

import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.FakeUserRepository;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private UserRepository userRepository;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        loginService = new LoginService(userRepository);
    }

    @Test
    @DisplayName("성공적으로 사용자를 로드한다.")
    void loadUserByUsername_success() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        // when
        String username = user.getUsername();
        UserPrincipal userPrincipal = (UserPrincipal) loginService.loadUserByUsername(username);

        // then
        assertNotNull(userPrincipal);
        assertEquals(user.getUsername(), userPrincipal.getUsername());
        assertEquals(user.getPassword(), userPrincipal.getPassword());
        assertEquals(user.getRole().toString(), userPrincipal.getRole());
    }

    @Test
    @DisplayName("사용자를 찾을 수 없을 때 예외를 던진다.")
    void loadUserByUsername_userNotFound() {
        // given
        String username = "nonExistentUser";

        // when
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            loginService.loadUserByUsername(username);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }
}