package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteTypeQ;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/user/question")
public class QuestionController {

    private final CommentDtoService commentDtoService;
    private final QuestionService questionService;


    public QuestionController(CommentDtoService commentDtoService, QuestionService questionService) {
        this.commentDtoService = commentDtoService;
        this.questionService = questionService;
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getAllCommentsOnQuestion(@PathVariable(value = "id") Long questionId) {
        if (questionService.existsById(questionId)) {
            List<QuestionCommentDto> comments = commentDtoService.getAllQuestionCommentDtoById(questionId);
            if (comments.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(comments);
            }
        }
        return ResponseEntity.badRequest().body("Question with id: " + questionId + " not found");
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCountQuestion() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(questionService.getCountQuestion());
    }
}