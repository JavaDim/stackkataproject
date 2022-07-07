package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends ReadWriteDaoImpl<Tag, Long> implements TagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Tag>> getTagsByNames(List<String> names) {
        return Optional.ofNullable(entityManager.createQuery("SELECT t FROM Tag t WHERE t.name = :names", Tag.class)
                .setParameter("names", names)
                .getResultList());
    }
}
