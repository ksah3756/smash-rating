package com.smashrating.notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.smashrating.notification.NotificationSender;
import com.smashrating.notification.dto.NotificationRequest;
import com.smashrating.notification.exception.NotificationErrorCode;
import com.smashrating.notification.exception.NotificationException;
import com.smashrating.redis.RedisKeyPrefix;
import com.smashrating.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationSender implements NotificationSender {

    private final FirebaseMessaging firebaseMessaging;
    private final RedisRepository redisRepository;

    @Override
    public void send(NotificationRequest request) {
        String token = (String) redisRepository.get(RedisKeyPrefix.FCM_TOKEN + request.userId());
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.body())
                        .build())
                .build();

        String response = null;
        try {
            response = firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Error sending FCM message: {}", e.getMessage());
            throw new NotificationException(NotificationErrorCode.SEND_FAILED, e.getMessage());
        }
        log.info("Successfully sent FCM message: {}", response);
    }

    @Override
    public void saveToken(Long userId, String token) {
        redisRepository.save(RedisKeyPrefix.FCM_TOKEN + userId, token);
    }
}
