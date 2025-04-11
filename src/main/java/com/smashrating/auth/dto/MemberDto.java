package com.smashrating.auth.dto;

public record MemberDto(
        String role,
        String name,
        String username,
        String password
) {
    public static MemberDto of(String role, String name, String username, String password) {
        return new MemberDto(role, name, username, password);
    }
}
