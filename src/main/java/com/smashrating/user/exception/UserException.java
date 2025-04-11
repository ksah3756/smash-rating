package com.smashrating.user.exception;

import com.smashrating.common.exception.CustomException;

public class UserException extends CustomException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
