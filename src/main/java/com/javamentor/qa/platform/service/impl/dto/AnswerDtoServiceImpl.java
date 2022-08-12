package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AnswerDtoServiceImpl implements AnswerDtoService {
    private final AnswerDtoDao answerDtoDao;

    public AnswerDtoServiceImpl(AnswerDtoDao answerDtoDao) {
        this.answerDtoDao = answerDtoDao;
    }

    @Override
    @Transactional
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        return answerDtoDao.getAllAnswersDtoByQuestionId(questionId, userId);
    }
}
