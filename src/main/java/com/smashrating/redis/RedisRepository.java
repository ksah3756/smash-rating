package com.smashrating.redis;

public interface RedisRepository {
    void save(String key, Object value);
    Object get(String key);
    void delete(String key);
    boolean exists(String key);
}
