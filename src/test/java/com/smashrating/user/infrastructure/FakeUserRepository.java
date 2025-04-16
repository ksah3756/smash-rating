package com.smashrating.user.infrastructure;

import com.smashrating.user.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public User save(User user) {
        User savedUser = User.builder()
                .id(sequence.getAndIncrement())
                .username(user.getUsername())
                .password(user.getPassword())
                .realName(user.getRealName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        users.add(savedUser);
        return savedUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsByNickname(String name) {
        return users.stream()
                .anyMatch(user -> user.getNickname().equals(name));
    }
}
