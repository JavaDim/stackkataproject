package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class UserServiceImpl <E, K>extends ReadWriteServiceImpl <E, K>{

    private final UserDao<E, K> userDao;

    public UserServiceImpl(UserDao<E, K> userDao) {
        super((userDao));
        this.userDao = userDao;
    }

    @Transactional
    public Optional<E> getByEmail(K email) {
        return userDao.getByEmail(email);
    }
}
