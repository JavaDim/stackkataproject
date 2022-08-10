package com.javamentor.qa.platform.webapp.controllers.resource;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import com.javamentor.qa.platform.webapp.controllers.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestResourceUserController extends BaseTest {

    private final EntityManager entityManager;

    private final UserServiceImpl userService;

    @Autowired
    public TestResourceUserController(EntityManager entityManager, UserServiceImpl userService) {
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Test
    @Rollback(false)
    @Transactional
    @DisplayName("GET /api/user/110 - 200 status check and correct UserDto fields check")
    public void givenExistingUser_whenUserInfoIsRetrieved_thenReturnCode200AndShowUserDto() throws Exception {

        // GIVEN
        String seedDatabaseQuery = """
                INSERT INTO role (id, name) VALUES (110, 'ADMIN');
                                
                INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
                VALUES  (110, 'test user 1', 'Medina', 'bill@mail.ru', 'Bill Gates', 'test image link', FALSE, TRUE, '2022-07-29 14:02:02.030177',
                        'github@bill.gates', 'microsoft.com', 'vk.com/bill', 'Trey', 'password', '2022-07-28 14:02:02.030177', 110),
                        (111, 'test user 2', 'Austin', 'elon@mail.ru', 'Elon Musk', 'test image link', FALSE, TRUE, '2022-07-29 14:02:02.030177',
                        'github@elon.musk', 'tesla.com', 'vk.vk.com/elon', 'TechnoKing', 'password', '2022-07-28 14:02:02.030177', 110);
                        
                INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
                VALUES (110, 'test description', FALSE, '2022-07-28 14:02:02.030177', '2022-07-28 14:02:02.030177', 'test title', 110);
                                
                INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
                VALUES (110, '2022-07-28 14:02:02.030177', 'test html body', FALSE, FALSE, TRUE, '2022-07-28 14:02:02.030177', '2022-07-28 14:02:02.030177', 110, 110); 
                                
                INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id) 
                VALUES (110, 5, '2022-07-28 14:02:02.030177', 1, 110, 110, 110, 111),
                       (111, 5, '2022-07-28 14:02:02.030177', 1, 110, 110, 110, 111),
                       (112, 5, '2022-07-28 14:02:02.030177', 1, 110, 111, 110, 110);
                                
                INSERT INTO votes_on_answers (id, persist_date, vote_type, answer_id, user_id) 
                VALUES (110, '2022-07-28 14:02:02.030177', 'UP', 110, 110),
                       (111, '2022-07-28 14:02:02.030177', 'UP', 110, 111),
                       (112, '2022-07-28 14:02:02.030177', 'DOWN', 110, 111),
                       (113, '2022-07-28 14:02:02.030177', 'UP', 110, 111);
                       
                INSERT INTO votes_on_questions(id, persist_date, vote_typeq, question_id, user_id) 
                VALUES (110, '2022-07-28 14:02:02.030177', 'UP', 110, 110),
                       (111, '2022-07-28 14:02:02.030177', 'DOWN', 110, 110),
                       (112, '2022-07-28 14:02:02.030177', 'DOWN', 110, 111),
                       (113, '2022-07-28 14:02:02.030177', 'UP', 110, 111);
                """;

        entityManager.createNativeQuery(seedDatabaseQuery).executeUpdate();


        Long userId = 110L;
        User user = userService.getById(userId).get();

        // WHEN
        ResultActions perform = mockMvc.perform(get("/api/user/{id}", userId));

        // THEN
        perform
                .andExpect(status().is2xxSuccessful())

                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.linkImage").value(user.getImageLink()))
                .andExpect(jsonPath("$.city").value(user.getCity()))
                .andExpect(jsonPath("$.reputation").value(10))
                .andExpect(jsonPath("$.registrationDate").value(user.getPersistDateTime().toString()))
                .andExpect(jsonPath("$.votes").value(3));

        // CLEAN DATABASE
        String cleanDatabaseQuery = """
                DELETE FROM votes_on_questions vq WHERE vq.id BETWEEN 110 AND 113;
                DELETE FROM votes_on_answers va WHERE va.id BETWEEN 110 AND 113;
                DELETE FROM reputation r WHERE r.id BETWEEN 110 AND 112;
                DELETE FROM answer a WHERE a.id = 110;
                DELETE FROM question q WHERE q.id = 110;
                DELETE FROM user_entity WHERE id BETWEEN 110 AND 111;
                DELETE FROM role WHERE role.id = 110;
                """;

        entityManager.createNativeQuery(cleanDatabaseQuery).executeUpdate();
    }
}

