package com.smashrating.auth.exception;

import com.smashrating.common.exception.CustomException;
import com.smashrating.common.exception.ErrorCode;

public class AuthException extends CustomException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
