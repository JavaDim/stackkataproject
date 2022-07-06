package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.RoleDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class RoleServiceImpl <E, K> extends ReadWriteServiceImpl <E, K>{
    private final RoleDao <E, K> roleDao;

    protected RoleServiceImpl(RoleDao<E, K> roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }


    @Transactional
    public Optional<E> getByName(K name) {
        return roleDao.getByName(name);
    }
}
