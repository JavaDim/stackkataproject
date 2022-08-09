package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/question")
public class QuestionController {
    private final CommentDtoService dtoService;
    private final QuestionService questionService;

    public QuestionController(CommentDtoService dtoService, QuestionService questionService) {
        this.dtoService = dtoService;
        this.questionService = questionService;
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<QuestionCommentDto>> getAllCommentsOnQuestion(@PathVariable Long id) {
        if (questionService.existsById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<QuestionCommentDto> commentsDto = dtoService.getAllQuestionCommentDtoById(id);
        if (commentsDto.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }
}
