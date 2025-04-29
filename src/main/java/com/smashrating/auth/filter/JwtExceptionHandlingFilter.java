package com.smashrating.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smashrating.auth.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            handleAuthException(response, e);
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }
    }

    private void handleUnexpectedException(HttpServletResponse response, Exception e) throws IOException {
        log.error("Unexpected error in filter chain: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "INTERNAL_SERVER_ERROR");
        errorResponse.put("message", "filter chain에서 예상치 못한 오류가 발생했습니다.");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void handleAuthException(HttpServletResponse response, AuthException e) throws IOException {
        log.error("Authentication error: {}", e.getMessage());
        response.setStatus(e.getErrorCode().getStatus().value());
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getErrorCode().getCode());
        errorResponse.put("message", e.getErrorCode().getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
