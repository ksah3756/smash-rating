package com.smashrating.auth.filter;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.dto.UserPrinciple;
import com.smashrating.auth.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
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
    void validToken_setsAuthenticationInSecurityContext() throws Exception {
        // given;
        UserDto userDto = UserDto.of("ROLE_USER", "testName", "testId", "testEmail");
        Authentication auth = new UsernamePasswordAuthenticationToken(UserPrinciple.create(userDto), null, List.of());
        String accessToken = jwtProvider.generateAccessToken(auth);

        mockMvc.perform(get("/test")
                        .cookie(new Cookie("accessToken", accessToken)))
                .andExpect(status().isOk());
    }

    @Test
    void missingToken_doesNotSetAuthentication() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isUnauthorized());
    }
}