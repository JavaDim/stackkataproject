package com.javamentor.qa.platform.webapp.configs;

import com.javamentor.qa.platform.webapp.configs.initializer.TestEntityInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan("com.javamentor.qa.platform")
@EntityScan("com.javamentor.qa.platform.models.entity")
public class JmApplication {

    private final Logger logger = LoggerFactory.getLogger(JmApplication.class);

    @Autowired
    private TestEntityInit testEntityInit;

    public static void main(String[] args) {
        SpringApplication.run(JmApplication.class, args);
    }

    private void testInit() {
        testEntityInit.run();
    }
}
