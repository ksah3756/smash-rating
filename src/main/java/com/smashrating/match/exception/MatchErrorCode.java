package com.smashrating.match.exception;

import com.smashrating.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MatchErrorCode implements ErrorCode {

    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "Match not found"),
    MATCH_ALREADY_EXISTS(HttpStatus.CONFLICT, "Match already exists"),
    MATCH_NOT_RECEIVER(HttpStatus.FORBIDDEN, "You are not the receiver of this match"),
    MATCH_ALREADY_ACCEPTED(HttpStatus.CONFLICT, "Match already accepted"),;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.toString();
    }
}
