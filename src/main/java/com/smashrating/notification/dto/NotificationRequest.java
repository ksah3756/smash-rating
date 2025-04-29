package com.smashrating.notification.dto;

public record NotificationRequest(
        String username,
        String title,
        String body
) {
    public static NotificationRequest of(String username, String title, String body) {
        return new NotificationRequest(username, title, body);
    }
}
