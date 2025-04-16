package com.smashrating.auth.dto;

public record UserDto(
        String role,
        Long id,
        String username,
        String password
) {
    public static UserDto of(String role, Long id, String username, String password) {
        return new UserDto(role, id, username, password);
    }
}
