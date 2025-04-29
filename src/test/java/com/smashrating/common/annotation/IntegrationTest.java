package com.smashrating.common.annotation;

import com.smashrating.auth.TestSecurityConfig;
import com.smashrating.config.TestContainerConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Testcontainers
@Import({TestSecurityConfig.class, TestContainerConfig.class})
public @interface IntegrationTest {
}
