package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;

import java.util.List;

public interface AnswerDtoService {
    List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId);
}
