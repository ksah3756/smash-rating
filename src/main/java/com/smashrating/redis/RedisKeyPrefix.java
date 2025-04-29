package com.smashrating.redis;

public final class RedisKeyPrefix {
    public static final String FCM_TOKEN = "fcm:token:";
    public static final String REFRESH_TOKEN = "refresh_token:";

    private RedisKeyPrefix() {} // 인스턴스화 방지
}
