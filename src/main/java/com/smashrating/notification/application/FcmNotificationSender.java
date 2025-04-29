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

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationSender implements NotificationSender {

    private final FirebaseMessaging firebaseMessaging;
    private final RedisRepository redisRepository;

    @Override
    public void send(NotificationRequest request) {
        String token = (String) redisRepository.get(RedisKeyPrefix.FCM_TOKEN + request.username());
        if (token == null) {
            log.warn("FCM token not found for user: {}", request.username());
            throw new NotificationException(NotificationErrorCode.TOKEN_NOT_FOUND, "FCM token not found for user: " + request.username());
        }
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
    public void saveToken(String username, String token) {
        // FCM 토큰 30일 동안 저장
        redisRepository.saveWithExpiration(RedisKeyPrefix.FCM_TOKEN + username, token, 30, TimeUnit.DAYS);
    }
}
