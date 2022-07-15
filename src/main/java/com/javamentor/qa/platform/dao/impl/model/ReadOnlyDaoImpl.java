package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyDaoImpl<E, K> {

    private Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @PersistenceContext
    private EntityManager entityManager;

    public List<E> getAll() {
        return entityManager.createQuery("FROM " + clazz.getName(), clazz).getResultList();
    }

    public boolean existsById(K id) {
        long count = (long) entityManager.createQuery("SELECT COUNT(e) FROM " + clazz.getName() + " e WHERE e.id =: id").setParameter("id", id).getSingleResult();
        return count > 0;
    }

    public Optional<E> getById(K id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("FROM " + clazz.getName() + " WHERE id = :id", clazz)
                .setParameter("id", id));
    }

    public List<E> getAllByIds(Iterable<K> ids) {
        return ids != null && ids.iterator().hasNext()
                ? entityManager
                .createQuery("FROM " + clazz.getName() + " WHERE id IN :ids", clazz)
                .setParameter("ids", ids)
                .getResultList()
                : new ArrayList<>();
    }

    public boolean existsByAllIds(Collection<K> ids) {
        if (ids != null && !ids.isEmpty()) {
            return ids.size() == (Long) entityManager.createQuery("SELECT COUNT (*) FROM " + clazz.getName() + " e WHERE e.id IN :ids")
                    .setParameter("ids", ids).getSingleResult();
        }
        return false;
    }

    public Optional<User> getByEmail(String email) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT u
                        FROM User u
                        JOIN FETCH u.role
                        WHERE u.email = :email
                        """, User.class)
                .setParameter("email", email));
    }
}
