package com.smashrating.notification.dto;

public record NotificationRequest(
        Long userId,
        String title,
        String body
) {
}
