package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Service
public class TestDataInitService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createEntity() {
        Role role_admin = new Role("ROLE_ADMIN");
        Role role_user = new Role("ROLE_USER");
        User admin = new User(
                1L,
                "admin@mail.ru",
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
                role_admin);

        entityManager.persist(role_admin);
        entityManager.persist(role_user);
        entityManager.persist(admin);
    }
}


