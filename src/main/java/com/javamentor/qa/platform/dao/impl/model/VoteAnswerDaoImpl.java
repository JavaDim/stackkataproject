package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long sumVote(Long answerId) {
        TypedQuery<Long> typedQuery = entityManager.createQuery("""
                        SELECT COUNT (va.voteType)
                        AS count
                        FROM VoteAnswer va
                        WHERE va.answer.id = :answerId
                        """, Long.class)
                .setParameter("answerId", answerId);
        return typedQuery.getSingleResult();
    }

    @Override
    public Optional<VoteAnswer> getByAnswerIdAndUserId(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT v
                        FROM VoteAnswer v
                        WHERE v.answer.id=:answerId
                        AND v.user.id=:userId
                        """, VoteAnswer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId));
    }
}
