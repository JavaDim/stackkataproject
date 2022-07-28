package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class TrackedTagDaoImpl extends ReadWriteDaoImpl<Tag, User> implements TrackedTagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Question> getByUserAndTag(User user, Tag tag) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                "SELECT q FROM  question WHERE question.tags = tag.id and WHERE question.user = user.id", Question.class)
                .setParameter(" tag.id",  tag.getId())
                .setParameter("user.id", user.getId()));
    }
}

