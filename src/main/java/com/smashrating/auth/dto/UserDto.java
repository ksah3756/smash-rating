package com.smashrating.auth.dto;

public record UserDto(
        String role,
        String name,
        String username,
        String password
) {
    public static UserDto of(String role, String name, String username, String password) {
        return new UserDto(role, name, username, password);
    }
}
