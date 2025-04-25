package com.smashrating.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserDto(
        String role,
        Long id,
        String username,
        @JsonIgnore String password
) {
    public static UserDto of(String role, Long id, String username, String password) {
        return new UserDto(role, id, username, password);
    }
}
