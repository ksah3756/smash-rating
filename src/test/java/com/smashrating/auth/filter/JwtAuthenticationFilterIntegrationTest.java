package com.smashrating.auth.filter;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.auth.jwt.JwtProvider;
import com.smashrating.common.annotation.IntegrationTest;
import com.smashrating.config.TestContainerConfig;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestContainerConfig.class)
class JwtAuthenticationFilterIntegrationTest {

    @TestConfiguration
    static class TestControllerConfig {
        @Bean
        public JwtTestTargetController jwtTestTargetController() {
            return new JwtTestTargetController();
        }
    }

    @RestController
    @RequestMapping("/test")
    static class JwtTestTargetController {
        @GetMapping
        public ResponseEntity<String> test() {
            return ResponseEntity.ok("authenticated");
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("유효한 토큰을 검증하고 200을 반환한다.")
    void validToken_setsAuthenticationInSecurityContext() throws Exception {
        // given;
        UserDto userDto = UserDto.of("ROLE_USER", 1L, "testName", "testId");
        Authentication auth = new UsernamePasswordAuthenticationToken(UserPrincipal.create(userDto), null, List.of());
        String accessToken = jwtProvider.generateAccessToken(auth);

        mockMvc.perform(get("/test")
                        .cookie(new Cookie("accessToken", accessToken)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("토큰이 없는 경우 401을 반환한다.")
    void missingToken_doesNotSetAuthentication() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isUnauthorized());
    }
}