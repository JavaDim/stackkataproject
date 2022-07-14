package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long>
        implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestion> getByUserAndQuestion(User user, Question question) {
        TypedQuery<VoteQuestion> voteQuestionTypedQuery = entityManager.createQuery("""
                        SELECT v
                        FROM VoteQuestion v
                        WHERE v.user.id=:userId
                        AND v.question.id=:questionId
                        """, VoteQuestion.class)
                .setParameter("userId", user.getId())
                .setParameter("questionId", question.getId());

        return SingleResultUtil.getSingleResultOrNull(voteQuestionTypedQuery);
    }

    @Override
    public Long getSumVoteQuestion(Question question) {
        TypedQuery<Long> questionTypedQuery = entityManager
                .createQuery("""
                        SELECT nullif(SUM (VoteQuestion.vote), 0)
                        FROM VoteQuestion v
                        WHERE v.question.id=:questionId
                        """, Long.class)
                .setParameter("questionId", question.getId());

        return questionTypedQuery.getSingleResult();
    }
}
