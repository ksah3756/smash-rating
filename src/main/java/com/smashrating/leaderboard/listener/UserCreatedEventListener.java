package com.smashrating.leaderboard.listener;

import com.smashrating.leaderboard.application.LeaderboardRepository;
import com.smashrating.user.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserCreatedEventListener {
    private final LeaderboardRepository leaderboardRepository;

    private static final Double INITIAL_SCORE = 1000.0;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(
            value = { RedisConnectionFailureException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2.0, random = true)
    )
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        leaderboardRepository.addScore(event.getUsername(), INITIAL_SCORE);
        log.info("User added to leaderboard: {}", event.getUsername());
    }

    @Recover
    public void recover(RedisConnectionFailureException ex, UserCreatedEvent event) {
        log.error("Failed to add user to leaderboard: {}, event: {}", ex.getMessage(), event);
    }
}
