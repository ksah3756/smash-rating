package com.smashrating.user.implement;

import com.smashrating.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public boolean isUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNameDuplicate(String name) {
        return userRepository.existsByName(name);
    }
}
