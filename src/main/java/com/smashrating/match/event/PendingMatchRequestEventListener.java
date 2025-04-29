package com.smashrating.match.event;

import com.smashrating.notification.NotificationSender;
import com.smashrating.notification.dto.NotificationRequest;
import com.smashrating.notification.exception.NotificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class PendingMatchRequestEventListener {

    private final NotificationSender notificationSender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(
            value = { NotificationException.class }, // Specify the exceptions to retry
            maxAttempts = 3, // Maximum number of retry attempts
            backoff = @Backoff(delay = 1000, multiplier = 2.0, random = true) // Backoff strategy
    )
    public void handlePendingMatchRequestEvent(PendingMatchRequestEvent event) {
        log.info("Pending match request event received: {}", event);
        StringBuilder sb = new StringBuilder();
        String message = sb.append(event.getSenderNickname()).append("(").append(event.getSenderRealName()).append(")")
                .append(" 님이 매치 요청을 보냈습니다.").toString();

        notificationSender.send(NotificationRequest.of(
                event.getReceiverUsername(),
                "매치 요청",
                message
        ));
    }

    @Recover
    public void recover(NotificationException e, PendingMatchRequestEvent event) {
        log.error("Failed to send notification after retries: {}", e.getMessage());
    }
}
