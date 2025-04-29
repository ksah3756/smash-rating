package com.smashrating.user.infrastructure;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return "";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
