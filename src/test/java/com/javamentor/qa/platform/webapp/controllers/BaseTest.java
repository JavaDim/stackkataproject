package com.javamentor.qa.platform.webapp.controllers;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = JmApplication.class)
@ActiveProfiles("test")
@DBRider
@AutoConfigureMockMvc
public abstract class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(json, clazz);
    }
}



