package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long getCountQuestion() {
        return entityManager.createQuery("""
                        SELECT COUNT(q)
                        FROM Question q
                        WHERE q.isDeleted = false
                        """, Long.class)
                .getSingleResult();
    }
}
