package com.smashrating.auth.enums.util;

import jakarta.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie createCookie(String name, String value, int expiry, boolean secure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expiry);
        if(secure) {
            cookie.setSecure(true);
            cookie.setAttribute("SameSite", "Strict");
        }
        return cookie;
    }

    public static Cookie createCookie(String name, String value, int expiry) {
        return createCookie(name, value, expiry, false);
    }
}
