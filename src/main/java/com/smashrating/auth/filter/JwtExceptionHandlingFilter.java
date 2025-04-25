package com.smashrating.auth.filter;

import com.smashrating.auth.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            log.error("Authentication error: {}", e.getMessage());
            response.setStatus(e.getErrorCode().getStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(String.format(
                    "{\"error\":\"%s\",\"message\":\"%s\"}",
                    e.getErrorCode().getCode(),
                    e.getErrorCode().getMessage())
            );
        }
    }
}
