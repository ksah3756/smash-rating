package com.smashrating.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public class TestContainerConfig {
    private static final int REDIS_PORT = 6379;
    static final MySQLContainer<?> mysqlContainer;
    static final GenericContainer<?> redisContainer;

    static {
        mysqlContainer = new MySQLContainer<>("mysql:8.0.26")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
        mysqlContainer.start();

        redisContainer = new GenericContainer<>("redis:7.4-alpine")
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);
        redisContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(REDIS_PORT));
    }
}
