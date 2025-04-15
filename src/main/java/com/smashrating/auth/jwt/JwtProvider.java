package com.smashrating.auth.jwt;

import com.smashrating.auth.dto.UserPrinciple;
import com.smashrating.auth.enums.util.JwtUtils;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

import static com.smashrating.auth.enums.TokenExp.ACCESS_TOKEN_EXP;
import static com.smashrating.auth.enums.TokenExp.REFRESH_TOKEN_EXP;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtUtils jwtUtils;

    private String generateToken(String username, Duration expiredAt, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());
        return makeToken(now, expiry, username, role);
    }

    private String makeToken(Date now, Date expiry, String username, String role) {
        return Jwts.builder()
                .issuer(jwtUtils.getIssuer())
                .issuedAt(now)
                .expiration(expiry)
                .subject(username)
                .claim("username", username)
                .claim("role", role)
                .signWith(jwtUtils.getSignInKey())
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        return generateToken(principal.getUsername(), ACCESS_TOKEN_EXP.getExp(), principal.getRole().toString());
    }

    public String generateRefreshToken(Authentication authentication) {
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        return generateToken(principal.getUsername(), REFRESH_TOKEN_EXP.getExp(), principal.getRole().toString());
    }


}
