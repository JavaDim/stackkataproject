package com.javamentor.qa.platform.controller;

import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.impl.model.AnswerServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
@ApiOperation("Контролер обработки ответов")
public class ResourceAnswerController {
    private AnswerServiceImpl answerService;
    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;
    public ResourceAnswerController(AnswerServiceImpl answerService, AnswerDtoService answerDtoService, QuestionService questionService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
    }

    @GetMapping("{answerId}")
    public ResponseEntity<?> deleteByAnswerId(@PathVariable("answerId") Long answerId) {
        if (answerService.getById(answerId).isPresent()) {
            answerService.deleteById(answerId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Получения списка ответов по id вопроса", notes = "Возвращает список ответов по id вопроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Данные получены"),
            @ApiResponse(code = 404, message = "Вопрос не найден")
    })
    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswer(@PathVariable("questionId") @ApiParam(name = "questionId", value = "ID вопроса", example = "1") Long questionId, User user) {
        if (questionService.existsById(questionId)) {
            return new ResponseEntity<>(answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with id: " + questionId + " not found");
        }
    }
}
