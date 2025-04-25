package com.smashrating.auth.exception;

import com.smashrating.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired token"),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.toString();
    }
}
