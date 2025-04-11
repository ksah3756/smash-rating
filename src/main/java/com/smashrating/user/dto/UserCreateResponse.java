package com.smashrating.user.dto;

public record UserCreateResponse(
        Long id
) {
    public static UserCreateResponse of(Long id) {
        return new UserCreateResponse(id);
    }
}
