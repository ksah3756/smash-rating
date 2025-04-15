package com.smashrating.auth.enums.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookieExp {
    ACCESS_TOKEN(60 * 60),
    REFRESH_TOKEN(60 * 60 * 24)
    ;
    private final int expiry;
}
