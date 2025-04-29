package com.smashrating.auth.jwt;

import com.smashrating.auth.enums.util.JwtUtils;
import com.smashrating.auth.exception.AuthException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JwtParserTest {

    private static JwtParser jwtParser;
    private static SecretKey key;
    private String validUserToken;
    private String validAdminToken;
    private String invalidToken;

    // DummyJwtUtils: 테스트를 위해 JwtUtils 인터페이스(또는 클래스)의 getSignInKey() 메서드만 구현
    private static class DummyJwtUtils extends JwtUtils {
        @Override
        public SecretKey getSignInKey() {
            return key;
        }
    }

    @BeforeAll
    static void setUp() {
        // HS256 알고리즘에 필요한 256비트(32바이트) 키 생성
        String secret = "01234567890123456789012345678901"; // 32자리 문자열
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtParser = new JwtParser(new DummyJwtUtils());
    }

    @BeforeEach
    void init() {
        // 유효한 토큰 생성: subject, username, role 값을 포함
        validUserToken = Jwts.builder()
                .subject("testUser")
                .claim("id", 1L)
                .claim("username", "testUser")
                .claim("role", "USER")
                .signWith(key)
                .compact();

        validAdminToken = Jwts.builder()
                .subject("adminUser")
                .claim("id", 2L)
                .claim("username", "adminUser")
                .claim("role", "ADMIN")
                .signWith(key)
                .compact();

        // 의미없는 토큰 문자열
        invalidToken = "this.is.an.invalid.token";
    }

    @Test
    @DisplayName("유효한 토큰이 주어지면 예외를 반환하지 않고 넘어간다.")
    void validateToken_validToken() {
        jwtParser.validateToken(validUserToken);
    }

    @Test
    @DisplayName("유효하지 않은 토큰이 주어지면 예외를 던진다.")
    void validateToken_invalidToken() {
        Assertions.assertThatThrownBy(() -> jwtParser.validateToken(invalidToken))
                        .isInstanceOf(AuthException.class)
                        .hasMessageContaining("Invalid token");
    }

    @Test
    @DisplayName("만료된 토큰이 주어지면 예외를 던진다.")
    void validateToken_expiredToken() {
        String expiredToken = Jwts.builder()
                .subject("testUser")
                .claim("username", "testUser")
                .claim("role", "USER")
                .expiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(key)
                .compact();

        Assertions.assertThatThrownBy(() -> jwtParser.validateToken(expiredToken))
                        .isInstanceOf(AuthException.class)
                        .hasMessageContaining("Expired token");
    }

    @Test
    @DisplayName("유저 JWT에서 Authentication 토큰을 추출한다.")
    void getAuthentication_userToken() {
        var authentication = jwtParser.getAuthentication(validUserToken);
        assertThat(authentication).isNotNull();
        assertThat(authentication.getName()).isEqualTo("testUser");
        assertThat(authentication.getAuthorities()).extracting("authority")
                .containsExactly("ROLE_USER");
    }

    @Test
    @DisplayName("관리자 JWT에서 Authentication 토큰을 추출한다.")
    void getAuthentication_adminToken() {
        var authentication = jwtParser.getAuthentication(validAdminToken);

        assertThat(authentication).isNotNull();
        assertThat(authentication.getName()).isEqualTo("adminUser");
        assertThat(authentication.getAuthorities()).extracting("authority")
                .containsExactly("ROLE_ADMIN");
    }

}