package com.jm.qa.platform.jm;

import com.javamentor.qa.platform.service.impl.TestDataInitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JmApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    @Rollback(false)
    void initDatabase(@Autowired TestDataInitService initService) {
        initService.createEntity();
    }
}
