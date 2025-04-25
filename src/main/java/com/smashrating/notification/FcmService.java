package com.smashrating.notification;

import com.smashrating.redis.RedisRepository;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final UserRepository userRepository;
    private final UserQue


    public ResponseEntity<?> saveToken(FCMTokenDto fcmTokenDto, long member_id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            User user = userRepository.findById(member_id)
                    .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

            redisRepository.save(user.getPhone(), fcmTokenDto.getToken());
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("exception", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
