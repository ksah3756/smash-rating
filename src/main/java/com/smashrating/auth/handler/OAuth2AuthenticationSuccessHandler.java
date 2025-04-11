package com.smashrating.auth.handler;

import com.smashrating.auth.jwt.JwtProvider;
import com.smashrating.auth.dto.MemberPrinciple;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    private final int ACCESS_TOKEN_COOKIE_EXP = 60 * 60;
    private final int REFRESH_TOKEN_COOKIE_EXP = 60 * 60 * 24;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberPrinciple oAuth2User = (MemberPrinciple) authentication.getPrincipal();

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        response.addCookie(createCookie("accessToken", accessToken, ACCESS_TOKEN_COOKIE_EXP));
        response.addCookie(createCookie("refreshToken", refreshToken, REFRESH_TOKEN_COOKIE_EXP));
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expiry);

        return cookie;
    }
}
