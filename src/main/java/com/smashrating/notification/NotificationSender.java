package com.smashrating.notification;

import com.smashrating.notification.dto.NotificationRequest;

public interface NotificationSender {
    void send(NotificationRequest request);
    void saveToken(String username, String token);
}
