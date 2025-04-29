package com.smashrating.redis;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {
    void save(String key, Object value);
    void saveWithExpiration(String key, Object value, long expiration, TimeUnit timeUnit);
    Object get(String key);
    void delete(String key);
    boolean exists(String key);
}
