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
                        SELECT a
                        FROM Answer a
                        WHERE a.id = :answerId
                        AND NOT a.user.id = :userId
                        """, Answer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId));
    }

    @Override
    public void updateAnswerSpecial(Answer answer) {
        entityManager.createQuery("""
                        UPDATE Answer a
                        SET a.htmlBody = :body,
                            a.updateDateTime = :time
                        WHERE a.id = :id
                        """)
                .setParameter("body", answer.getHtmlBody())
                .setParameter("time", LocalDateTime.now())
                .setParameter("id", answer.getId())
                .executeUpdate();
    }
}
