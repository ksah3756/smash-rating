package com.smashrating.user.exception;

import com.smashrating.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_USERNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "Username already exists"),
    USER_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "Email already exists"),
    USER_NAME_DUPLICATE(HttpStatus.BAD_REQUEST, "Name already exists"),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.toString();
    }
}
