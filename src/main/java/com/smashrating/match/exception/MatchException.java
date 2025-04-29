package com.smashrating.match.exception;

import com.smashrating.common.exception.CustomException;
import com.smashrating.common.exception.ErrorCode;

public class MatchException extends CustomException {
    public MatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
