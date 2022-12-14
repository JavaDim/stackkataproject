package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;

import java.util.List;

public interface AnswerDtoDao {
    List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId);
}
