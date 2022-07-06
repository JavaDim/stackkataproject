package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.models.entity.user.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoleDaoImpl extends ReadWriteDaoImpl {
    @PersistenceContext
    protected EntityManager entityManager;

    public Role getByName(String name) {
        return entityManager.find(Role.class, name);
    }
}
