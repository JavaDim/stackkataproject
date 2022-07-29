package com.javamentor.qa.platform.webapp.configs;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.TestDataInitService;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
//@RequiredArgsConstructor
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@ContextConfiguration(classes = {TestDataInitService.class})
//@ExtendWith(SpringExtension.class)
class JmApplicationTests {


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

}
