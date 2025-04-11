package com.smashrating.user.implement;

import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = { // core 모듈의 domain package들을 scan
        "com.smashrating.user.domain"
})
@EnableJpaRepositories(basePackages = { // core 모듈의 infrastructure package들을 scan
       "com.smashrating.user.infrastructure"
})
@Import({UserWriter.class, UserWriterTest.TestConfig.class})
class UserWriterTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private UserWriter userWriter;

    @Test
    @DisplayName("비밀번호를 암호화하여 유저를 생성한다.")
    void createUser() {
        // given
        User user = User.create(
                "testUser",
                "testPassword",
                "testName",
                "testEmail@gmail.com",
                Role.ROLE_USER
        );

        // when
        User savedUser = userWriter.createUser(user.getUsername(), user.getPassword(), user.getName(), user.getEmail());

        // then
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(savedUser.getPassword()).isNotEqualTo(user.getPassword());
    }
}