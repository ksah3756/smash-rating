package com.smashrating.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationSenderType {
    FCM("fcmNotificationSender"),
    ;

    private final String senderName;
}
