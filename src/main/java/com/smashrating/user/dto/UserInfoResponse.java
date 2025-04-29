package com.smashrating.user.dto;

import com.smashrating.user.domain.User;

public record UserInfoResponse(
        Long id,
        String username,
        String realName,
        String nickname,
        String email
) {

    public static UserInfoResponse fromEntity(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getRealName(),
                user.getNickname(),
                user.getEmail()
        );
    }
}
