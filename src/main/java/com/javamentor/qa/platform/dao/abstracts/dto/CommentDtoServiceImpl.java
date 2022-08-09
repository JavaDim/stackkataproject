package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentDtoServiceImpl implements CommentDtoService{
    private final CommentDtoDao commentDtoDao;
    public CommentDtoServiceImpl(CommentDtoDao commentDtoDao) {
        this.commentDtoDao = commentDtoDao;
    }
    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {
        return commentDtoDao.getAllQuestionCommentDtoById(questionId);
    }
}
