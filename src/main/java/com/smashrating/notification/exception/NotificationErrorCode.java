package com.smashrating.notification.exception;

import com.smashrating.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

    SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send notification"),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.toString();
    }
}
