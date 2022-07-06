package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.RoleDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class RoleDaoImpl extends ReadWriteDaoImpl<Role, Long> implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Role> getByName(String name) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name")
                .setParameter("name", name));
    }
}
