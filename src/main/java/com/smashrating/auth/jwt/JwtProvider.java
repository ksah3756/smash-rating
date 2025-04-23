package com.smashrating.auth.jwt;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.dto.UserPrincipal;
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

    private String generateToken(UserDto userDto, Duration expiredAt) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());
        return makeToken(now, expiry, userDto);
    }

    private String makeToken(Date now, Date expiry, UserDto userdto) {
        return Jwts.builder()
                .issuer(jwtUtils.getIssuer())
                .issuedAt(now)
                .expiration(expiry)
                .subject(userdto.username())
                .claim("id", userdto.id())
                .claim("username", userdto.username())
                .claim("role", userdto.role())
                .signWith(jwtUtils.getSignInKey())
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return generateToken(principal.getUserDto(), ACCESS_TOKEN_EXP.getExp());
    }

    public String generateRefreshToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return generateToken(principal.getUserDto(), REFRESH_TOKEN_EXP.getExp());
    }


}
