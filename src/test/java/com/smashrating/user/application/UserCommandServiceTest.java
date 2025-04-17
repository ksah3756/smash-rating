package com.smashrating.user.application;

import com.smashrating.user.application.command.UserCommandService;
import com.smashrating.user.domain.User;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.infrastructure.FakePasswordEncoder;
import com.smashrating.user.infrastructure.FakeUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserCommandServiceTest {

    private UserCommandService userWriter;

    @BeforeEach
    void setUp() {
        userWriter = new UserCommandService(new FakeUserRepository(), new FakePasswordEncoder());
    }

    @Test
    @DisplayName("비밀번호를 암호화하여 유저를 생성한다.")
    void createUser() {
        // given
        User user = UserTestFactory.createDefaultUser();

        // when
        User savedUser = userWriter.createUser(user.getUsername(), user.getPassword(), user.getRealName(), user.getNickname() ,user.getEmail());

        // then
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(savedUser.getPassword()).isNotEqualTo(user.getPassword());
    }
}