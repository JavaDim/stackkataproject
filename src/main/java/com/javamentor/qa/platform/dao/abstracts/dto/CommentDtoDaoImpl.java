package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {
        TypedQuery<QuestionCommentDto> query = entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.QuestionCommentDto(c.id, cq.question.id, c.lastUpdateDateTime, c.persistDateTime, c.text, c.user.id, c.user.imageLink, r.count) 
                FROM CommentQuestion cq 
                JOIN cq.comment c 
                JOIN Reputation r ON r.author.id = c.user.id
                WHERE cq.question.id=:questionId 
                """, QuestionCommentDto.class);
        query.setParameter("questionId", questionId);
        List<QuestionCommentDto> allQuestionCommentDto = query.getResultList();
        return allQuestionCommentDto;
    }
}
