package com.smashrating.rating.exception;

import com.smashrating.common.exception.CustomException;
import com.smashrating.common.exception.ErrorCode;

public class RatingException extends CustomException {
    public RatingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
