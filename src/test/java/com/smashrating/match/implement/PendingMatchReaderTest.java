package com.smashrating.match.implement;

import com.smashrating.common.config.QueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EntityScan(basePackages = { // core 모듈의 domain package들을 scan
        "com.smashrating.match.domain"
})
@EnableJpaRepositories(basePackages = { // core 모듈의 infrastructure package들을 scan
        "com.smashrating.match.infrastructure"
})
@Import({PendingMatchReader.class, QueryDslConfig.class})
class PendingMatchReaderTest {

    @Test
    void getReceivedPendingMatch() {
    }

    @Test
    void getSentPendingMatch() {
    }
}