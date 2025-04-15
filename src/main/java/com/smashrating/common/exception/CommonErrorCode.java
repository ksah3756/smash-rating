package com.smashrating.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Parameter is invalid"),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "Type not supported"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Could not find the resource"),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "Could not find the URL"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.toString();
    }
}
