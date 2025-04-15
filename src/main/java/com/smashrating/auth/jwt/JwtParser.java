package com.smashrating.auth.jwt;

import com.smashrating.auth.enums.util.JwtUtils;
import com.smashrating.auth.exception.AuthErrorCode;
import com.smashrating.auth.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtParser {
    private final JwtUtils jwtUtils;

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(jwtUtils.getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch(ExpiredJwtException e) {
            throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (RuntimeException e) {
            // Token이 유효하지 않은 경우, 예외를 던짐
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    public void validateToken(String token) {
        parseClaims(token);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String role = claims.get("role", String.class);
        Set<SimpleGrantedAuthority> authorities = getRoles(role);

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(),
                        "",
                        authorities
                ), token, authorities
        );
    }

    private Set<SimpleGrantedAuthority> getRoles(String role) {
        if (role.equals("ADMIN")) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.get("username", String.class);
    }
}
