package com.smashrating.user.implement;

import com.smashrating.user.domain.User;
import com.smashrating.user.domain.Role;
import com.smashrating.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWriter {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String username, String password, String realName, String nickname, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.save(User.create(username, encodedPassword, realName, nickname, email, Role.ROLE_USER));
    }
}
