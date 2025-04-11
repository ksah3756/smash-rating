package com.smashrating.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UserCreatedEvent {
    private final String username;

    public static UserCreatedEvent of(String username) {
        return new UserCreatedEvent(username);
    }
}
