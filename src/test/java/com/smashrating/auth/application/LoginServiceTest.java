package com.smashrating.auth.application;

import com.smashrating.auth.dto.UserPrinciple;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@Import({LoginService.class})
@Transactional
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("성공적으로 사용자를 로드한다.")
    void loadUserByUsername_success() {
        // given
        User user = UserTestFactory.createDefaultUser();
        userRepository.save(user);

        // when
        String username = user.getUsername();
        UserPrinciple userPrinciple = (UserPrinciple) loginService.loadUserByUsername(username);

        // then
        assertNotNull(userPrinciple);
        assertEquals(user.getUsername(), userPrinciple.getUsername());
        assertEquals(user.getPassword(), userPrinciple.getPassword());
        assertEquals(user.getName(), userPrinciple.getName());
        assertEquals(user.getRole().toString(), userPrinciple.getRole());
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