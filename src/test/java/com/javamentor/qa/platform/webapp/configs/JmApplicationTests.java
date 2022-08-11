package com.javamentor.qa.platform.webapp.configs;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.TestDataInitService;
import com.javamentor.qa.platform.service.impl.model.TagServiceImpl;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//@RequiredArgsConstructor
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@SpringBootApplication
public
//@ContextConfiguration(classes = {TestDataInitService.class})
//@ExtendWith(SpringExtension.class)
class JmApplicationTests {

    public static void main(String[] args) {
        SpringApplication.run(JmApplication.class, args);
    }


    @Test
    void contextLoads() {
    }

    @Test
    @Rollback(false)
    void initDatabase(@Autowired TestDataInitService testDataInitService, @Autowired UserServiceImpl userServiceImpl) {
        testDataInitService.createEntity();
        Optional<User> user = userServiceImpl.getByEmail("fdfd@mail.ru");
        Assert.assertFalse(user.isEmpty());
        user.get().setId(null);
        Assert.assertEquals(TestDataInitService.ADMIN,user.orElse(null));
    }
//    @Test
//    @Rollback(false)
//    void initCreateVotes(@Autowired TestDataInitService testDataInitService){
//        testDataInitService.createVotes();
//    }
    @Test
    @Rollback(false)
    void initCreateTags(@Autowired TestDataInitService testDataInitService){
        testDataInitService.createTags();
    }

    @Test
    @Rollback(false)
    void initCreateQuestions(@Autowired TestDataInitService testDataInitService,
                             @Autowired UserServiceImpl userServiceImpl,
                             @Autowired TagServiceImpl tagServiceImpl){
        int count = 3;
        Tag java = new Tag(
                1L,
                "Java",
                "Java (не путать с JavaScript) — строго типизированный объектно-ориентированный язык программирования. Приложения Java обычно транслируются в специальный байт-код, поэтому они могут работать на любой компьютерной архитектуре, с помощью виртуальной Java-машины (JVM). Используйте эту метку для вопросов, относящихся к языку Java или инструментам из платформы Java.",
                LocalDateTime.now(),
                new ArrayList<Question>());
        List<Tag> tags = new ArrayList<>();
        tags.add(java);
        List<String> names = new ArrayList<>() ;
        names.add("Java");
        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
        User user = new User(
                7L,
                "fdfd@mail.ru",
                "admin",
                "admin",
                LocalDateTime.now(),
                true,
                false,
                "City",
                "https://www.google.ru",
                "https://github.com",
                "https://vk.com",
                "About",
                "image link",
                LocalDateTime.now(),
                "admin",
                ROLE_ADMIN);
//        Assert.assertFalse(user.isEmpty());
        testDataInitService.createQuestions(user, tags,count);
    }

}
