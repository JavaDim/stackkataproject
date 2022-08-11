package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.TestDataInitService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class)
class QuestionControllerTest {
    private QuestionService questionService;
    private ObjectMapper objectMapper;
    private TestDataInitService testDataInitService;
    private TagService tagService;
    private UserService userService;
    private MockMvc mockMvc;

    @Autowired
    public QuestionControllerTest(QuestionService questionService,
                                  ObjectMapper objectMapper,
                                  TestDataInitService testDataInitService,
                                  TagService tagService,
                                  UserService userService,
                                  MockMvc mockMvc) {
        this.questionService = questionService;
        this.objectMapper = objectMapper;
        this.testDataInitService = testDataInitService;
        this.tagService = tagService;
        this.userService = userService;
        this.mockMvc = mockMvc;
    }

    @Test
    void getCountQuestion() throws Exception {

        Long count = questionService.getCountQuestion();

        mockMvc.perform(
                        get("/api/user/question/count")
                                .content(objectMapper.writeValueAsString(count))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(count)))
                .andDo(print());
    }

    @Test
    void createQuestions() {
        testDataInitService.createEntity();
        User user = userService.getByEmail("fdfd@mail.ru").get();
        List<String> names = new ArrayList<>();
        names.add("Java");
        List<Tag> tags =tagService.getTagsByNames(names).get();
        testDataInitService.createQuestions(user,tags,3);
    }

}
