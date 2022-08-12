package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        TypedQuery<AnswerDto> answerDtoList = entityManager.createQuery("""
                        select
                        a.id as id,
                        a.htmlBody as htmlBody,
                        a.persistDateTime as persistDateTime,
                        a.isHelpful as isHelpful,
                        a.dateAcceptTime as dateAcceptTime,
                        a.question.id as questionId,
                        a.user.id as userId,
                        a.user.imageLink as userImageLink,
                        a.user.nickname as userNickname,
                        (select coalesce(v.voteType, 'null') from VoteAnswer v where v.user.id = :userId and v.answer.id = a.id) as voteType,
                        (select count(v.id) from VoteAnswer v where v.answer.id = a.id) as voteCount,
                        (select coalesce(sum(case when (v.voteType = 'UP') then 1 when (v.voteType = 'DOWN') then -1 end), 0) from VoteAnswer v where v.answer.id = a.id) as ratingAnswer,
                        (select coalesce(sum(r.count), 0) from Reputation r WHERE r.author.id = a.user.id) as countUserReputation
                        from Answer a where a.question.id = :questionId and a.isDeleted = false
                        """, AnswerDto.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId);
        return answerDtoList.getResultList();
    }

}
