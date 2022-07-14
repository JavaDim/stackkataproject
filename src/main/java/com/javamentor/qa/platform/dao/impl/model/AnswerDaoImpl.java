package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Answer> getAnswerForVote(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        select a from Answer a
                        where a.id = :answerId
                        and not exists (
                            select v from VoteAnswer v
                            where v.answer.id = :answerId and v.user.id = :userId
                        )
                        """, Answer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId));
    }

    @Override
    public void updateAnswerSpecial(Long answerId) {
        LocalDateTime currentTime = LocalDateTime.now();
        entityManager.createQuery("""
                        update Answer as a
                        set a.isHelpful = true,
                            a.dateAcceptTime = :acceptDateTime,
                            a.updateDateTime = :updateDateTime
                        where a.id = :id
                        """)
                .setParameter("acceptDateTime", currentTime)
                .setParameter("updateDateTime", currentTime)
                .setParameter("id", answerId)
                .executeUpdate();
    }
}
