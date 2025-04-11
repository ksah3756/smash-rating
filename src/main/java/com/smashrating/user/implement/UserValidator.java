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
}
