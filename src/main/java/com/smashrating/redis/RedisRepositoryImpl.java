package com.smashrating.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveWithExpiration(String key, Object value, long expiration, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expiration, timeUnit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


}
