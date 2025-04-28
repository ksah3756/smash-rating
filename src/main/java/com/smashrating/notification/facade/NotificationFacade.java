package com.smashrating.notification.facade;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.smashrating.notification.NotificationSender;
import com.smashrating.notification.dto.NotificationRequest;
import com.smashrating.notification.enums.NotificationSenderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final Map<String, NotificationSender> senderMap;

    public void sendNotification(NotificationSenderType type, NotificationRequest request) {
        NotificationSender sender = senderMap.get(type.getSenderName());
        if (sender == null) {
            throw new IllegalArgumentException("No notification sender found for type: " + type.getSenderName());
        }
        sender.send(request);
    }

    public void saveToken(NotificationSenderType type, String username, String token) {
        NotificationSender sender = senderMap.get(type.getSenderName());
        if (sender == null) {
            throw new IllegalArgumentException("No notification sender found for type: " + type.getSenderName());
        }
        sender.saveToken(username, token);
    }
}
