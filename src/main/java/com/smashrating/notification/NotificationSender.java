package com.smashrating.notification;

import com.smashrating.notification.dto.NotificationRequest;

public interface NotificationSender {
    void send(NotificationRequest request);
    void saveToken(Long userId, String token);
}
