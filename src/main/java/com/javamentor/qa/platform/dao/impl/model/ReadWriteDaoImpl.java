package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class ReadWriteDaoImpl<E, K> extends ReadOnlyDaoImpl<E, K> {

    private static final String ENTITIES_MUST_NOT_BE_NULL = "Entities cannot be null and empty";
    private static final String USER_NOT_FOUND = "User is not found";

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @PersistenceContext
    protected EntityManager entityManager;

    public void persist(E e) {
        if (e == null) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }
        entityManager.persist(e);
        entityManager.flush();
    }

    public void update(E e) {
        if (e == null) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }
        entityManager.merge(e);
    }

    public void delete(E e) {
        if (e == null) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }
        entityManager.remove(e);
    }

    @SafeVarargs
    public final void persistAll(E... entities) {
        if (entities == null || entities.length == 0) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }

        this.persistAll(Arrays.stream(entities).collect(Collectors.toList()));
    }

    public void persistAll(Collection<E> entities) {
        if (entities == null || entities.isEmpty()) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }

        int counter = 0;
        for (E entity : entities) {
            if (counter > 0 && counter % this.batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(entity);
            counter++;
        }

        if (counter > 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    public void deleteAll(Collection<E> entities) {
        this.persistAll(entities);

        int counter = 0;
        for (E entity : entities) {
            if (counter > 0 && counter % this.batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.remove(entity);
            counter++;
        }

        if (counter > 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    public void updateAll(Iterable<? extends E> entities) {
        if (entities == null || !entities.iterator().hasNext()) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }

        int counter = 0;
        for (E entity : entities) {
            if (counter > 0 && counter % this.batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.merge(entity);
            counter++;
        }

        if (counter > 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    public void deleteById(K id) {
        E entity = entityManager.find(clazz, id);

        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public void updatePassword(User user) {
        if (user == null) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }
        if (!existsById((K) user.getId())) {
            throw new ConstrainException(USER_NOT_FOUND);
        }

        entityManager.createQuery("update User as u set u.password = :password where u.id = :id")
                .setParameter("password", user.getPassword())
                .setParameter("id", user.getId())
                .executeUpdate();
    }

    public void updateUserPublicInfo(User user) {
        if (user == null) {
            throw new ConstrainException(ENTITIES_MUST_NOT_BE_NULL);
        }
        if (!existsById((K) user.getId())) {
            throw new ConstrainException(USER_NOT_FOUND);
        }

        entityManager.createQuery("update User as u set " +
                        "u.nickname = :nickname, " +
                        "u.about = :about, " +
                        "u.imageLink = :imageLink, " +
                        "u.linkSite = :linkSite, " +
                        "u.linkVk = :linkVk, " +
                        "u.linkGitHub = :linkGitHub, " +
                        "u.fullName = :fullName, " +
                        "u.city = :city " +
                        "where u.id = :id")
                .setParameter("nickname", user.getNickname())
                .setParameter("about", user.getAbout())
                .setParameter("imageLink", user.getImageLink())
                .setParameter("linkSite", user.getLinkSite())
                .setParameter("linkVk", user.getLinkVk())
                .setParameter("linkGitHub", user.getLinkGitHub())
                .setParameter("fullName", user.getFullName())
                .setParameter("city", user.getCity())
                .setParameter("id", user.getId())
                .executeUpdate();
    }
}
