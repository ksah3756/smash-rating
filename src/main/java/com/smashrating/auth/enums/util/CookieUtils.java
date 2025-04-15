package com.smashrating.auth.enums.util;

import jakarta.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie createCookie(String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expiry);
//        cookie.setSecure(true);
//        cookie.setAttribute("SameSite", "Strict");

        return cookie;
    }
}
