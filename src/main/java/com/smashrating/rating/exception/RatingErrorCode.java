package com.smashrating.rating.exception;

import com.smashrating.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RatingErrorCode implements ErrorCode {

    RATING_NOT_FOUND(HttpStatus.NOT_FOUND, "Rating not found"),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.toString();
    }
}
