package com.smashrating.user.application;

import com.smashrating.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidatorService {

    private final UserRepository userRepository;

    public boolean isUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


}
