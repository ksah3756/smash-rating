package com.smashrating.user.event;

import com.smashrating.common.exception.CommonErrorCode;
import com.smashrating.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UserCreatedEvent {
    private final String username;

    public static UserCreatedEvent of(String username) {
        if(username == null || username.isEmpty()) {
            throw new CustomException(CommonErrorCode.INVALID_PARAMETER, "Username cannot be null or empty");
        }
        return new UserCreatedEvent(username);
    }
}
