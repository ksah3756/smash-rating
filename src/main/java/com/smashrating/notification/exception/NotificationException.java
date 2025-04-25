package com.smashrating.notification.exception;

import com.smashrating.common.exception.CustomException;
import com.smashrating.common.exception.ErrorCode;

public class NotificationException extends CustomException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotificationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
