package com.smashrating.auth.handler;

import com.smashrating.auth.jwt.JwtProvider;
import com.smashrating.auth.enums.util.CookieExp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.smashrating.auth.enums.util.CookieUtils.createCookie;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        response.addCookie(createCookie("accessToken", accessToken, CookieExp.ACCESS_TOKEN.getExpiry()));
        response.addCookie(createCookie("refreshToken", refreshToken, CookieExp.REFRESH_TOKEN.getExpiry()));
        response.sendRedirect("http://localhost:3000/");
    }
}
