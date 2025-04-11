package com.smashrating.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smashrating.auth.dto.TokenResponse;
import com.smashrating.auth.jwt.JwtProvider;
import com.smashrating.auth.util.CookieExp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

import static com.smashrating.auth.util.CookieUtils.createCookie;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        response.addCookie(createCookie("accessToken", accessToken, CookieExp.ACCESS_TOKEN.getExpiry()));
        response.addCookie(createCookie("refreshToken", refreshToken, CookieExp.REFRESH_TOKEN.getExpiry()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),
                TokenResponse.of(accessToken, refreshToken));

//        MemberPrinciple principal = (MemberPrinciple) authentication.getPrincipal();
//        eventPublisher.publishEvent(RefreshTokenSaveEvent.create(refreshToken, principal.getUsername(), principal.getRole()));

    }


}
