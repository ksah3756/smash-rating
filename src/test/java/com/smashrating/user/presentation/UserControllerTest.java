package com.smashrating.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smashrating.auth.TestSecurityConfig;
import com.smashrating.auth.config.SecurityConfig;
import com.smashrating.user.application.UserService;
import com.smashrating.user.dto.UserCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@Import({TestSecurityConfig.class})
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("요청 바디를 제대로 기입할 경우 회원가입을 성공한다.")
    void register_success() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .username("testuser")
                .password("testpass")
                .name("testName")
                .email("testEmail@gmail.com")
                .build();

        String req = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/user/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("요청 바디를 제대로 기입하지 않을 경우 회원가입에 실패한다.")
    void register_notValidRequestBody() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .username("")
                .password("testpass")
                .name("testName")
                .email("testEmail@gmail.com")
                .build();

        String req = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(post("/user/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("username")); // 예: username 필드에서 validation 실패
    }
}