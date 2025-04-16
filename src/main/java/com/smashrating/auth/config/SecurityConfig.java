package com.smashrating.auth.config;

import com.smashrating.auth.application.LoginService;
import com.smashrating.auth.filter.JwtExceptionHandlingFilter;
import com.smashrating.auth.filter.LoginFilter;
import com.smashrating.auth.handler.LoginSuccessHandler;
import com.smashrating.auth.jwt.JwtParser;
import com.smashrating.auth.jwt.JwtProvider;
import com.smashrating.auth.filter.JwtAuthenticationFilter;
import com.smashrating.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.smashrating.auth.oauth2.service.OAuth2LoginUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final JwtParser jwtParser;
    private final JwtProvider jwtProvider;
    private final OAuth2LoginUserService oAuth2LoginUserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final LoginService loginService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .cors(corsConfigurer ->
                        corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint((request, response, authException) -> {
                            log.error("Unauthorized error: {}", authException.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                        })
                )
                .formLogin(AbstractHttpConfigurer::disable) // UsernamePasswordAuthenticationFilter disable
                .oauth2Login(oauth2 ->
                        oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2LoginUserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                )
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlingFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_PATTERNS).permitAll()
                        .requestMatchers(STATIC_RESOURCES_PATTERNS).permitAll()
                        .requestMatchers(PERMIT_ALL_PATTERNS).permitAll()
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{\"code\":\"UNAUTHORIZED\",\"message\":\"인증이 필요합니다.\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "{\"code\":\"FORBIDDEN\",\"message\":\"접근 권한이 없습니다.\"}");
                        })
                );

        return http.build();
    }


    private static final String[] SWAGGER_PATTERNS = {
            "/swagger-ui/**",
            "/actuator/**",
            "/v3/api-docs/**",
    };

    private static final String[] STATIC_RESOURCES_PATTERNS = {
            "/img/**",
            "/css/**",
            "/js/**",
            "/cloud/**",
    };

    private static final String[] PERMIT_ALL_PATTERNS = {
            "/error",
            "/favicon.ico",
            "/index.html",
            "/",
    };

    private static final String[] PUBLIC_ENDPOINTS = {
        "/user/register",
    };

    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:8080"
            ));
            config.setAllowCredentials(true);
            config.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));
            config.setMaxAge(3600L);
            return config;
        };
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtParser);
    }

    public JwtExceptionHandlingFilter jwtExceptionHandlingFilter() {
        return new JwtExceptionHandlingFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(loginService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        return new ProviderManager(provider);
    }

    private LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/user/login");
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtProvider));
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }
}
