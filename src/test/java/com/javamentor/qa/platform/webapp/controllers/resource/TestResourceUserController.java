package com.javamentor.qa.platform.webapp.controllers.resource;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import com.javamentor.qa.platform.webapp.controllers.BaseTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TestResourceUserController extends BaseTest {

    private final UserServiceImpl userService;

    @Autowired
    public TestResourceUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @BeforeAll
    @DataSet("testresourceusercontroller/dataset.xml")
    static void seedDataBase() {
    }

    @Test
    @DisplayName("GET /api/user/100 - status 2xx check")
    public void givenExistingUserId_whenUserInfoIsRetrieved_thenReceiveStatus2xx() throws Exception {

        //GIVEN
        Long userId = 100L;

        //WHEN
        ResultActions perform = mockMvc.perform(get("/api/user/{id}", userId));

        //THEN
        perform
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/user/100 - correct UserDto fields check")
    public void givenExistingUser_whenUserInfoIsRetrieved_thenShowUserDto() throws Exception {

        // GIVEN
        Long userId = 100L;
        User user = userService.getById(userId).get();

        // WHEN
        ResultActions perform = mockMvc.perform(get("/api/user/{id}", userId));

        // THEN
        perform
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.linkImage").value(user.getImageLink()))
                .andExpect(jsonPath("$.city").value(user.getCity()))
                .andExpect(jsonPath("$.reputation").value(10))
                .andExpect(jsonPath("$.registrationDate").value(user.getPersistDateTime().toString()))
                .andExpect(jsonPath("$.votes").value(3));

    }

    @Test
    @DisplayName("GET /api/user/1000 - status 4xx check")
    public void givenWrongUserId_whenUserInfoIsRetrieved_thenReceiveStatus5xx() throws Exception {

        //GIVEN
        Long wrongUserId = 1000L;

        //WHEN
        ResultActions perform = mockMvc.perform(get("/api/user/{id}", wrongUserId));

        //THEN
        perform
                .andExpect(status().is4xxClientError());
    }
}


