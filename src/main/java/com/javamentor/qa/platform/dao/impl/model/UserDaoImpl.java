package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public class UserDaoImpl extends ReadWriteDaoImpl {
    @Override
    public Optional<User> getByEmail(String email) {
        return super.getByEmail(email);
    }
}