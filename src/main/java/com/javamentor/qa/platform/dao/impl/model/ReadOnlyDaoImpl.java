package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyDaoImpl<E, K> {

    private Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @PersistenceContext
    protected EntityManager entityManager;

    public List<E> getAll() {
        return entityManager.createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    public boolean existsById(K id) {
        return entityManager
                .createQuery("from " + clazz.getName() + " where id = :id")
                .setParameter("id", id)
                .getResultList().size() > 0;
    }

    public Optional<E> getById(K id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                        .createQuery("from " + clazz.getName() + " where id = :id", clazz)
                        .setParameter("id", id));
    }

    public List<E> getAllByIds(Iterable<K> ids) {
        return ids != null && ids.iterator().hasNext()
                ? entityManager
                        .createQuery("from " + clazz.getName() + " where id in :ids", clazz)
                        .setParameter("ids", ids)
                        .getResultList()
                : Collections.emptyList();
    }

    public boolean existsByAllIds(Collection<K> ids) {
        return ids != null && ids.size() > 0 && getAllByIds(ids).size() == ids.size();
    }

    public Optional<User> getByEmail(String email) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("from " + User.class.getName() + " where email = :email", User.class)
                .setParameter("email", email));
    }
}
