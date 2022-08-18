package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/user/question")
@Api(value = "Question Controller")
public class ResourceQuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final VoteQuestionService voteQuestionService;

    public ResourceQuestionController(QuestionService questionService, UserService userService, VoteQuestionService voteQuestionService) {
        this.questionService = questionService;
        this.userService = userService;
        this.voteQuestionService = voteQuestionService;
    }

    @ApiOperation(value = "Добавить 'положительный' голос за вопрос")
    @PostMapping("/{questionId}/upVote")
    public Long upVoteQuestion(@Parameter(description = "id вопроса, за который идет голосование", required = true)
                                   @PathVariable(value = "questionId") Long id, Principal principal) {
        User user = userService.getByEmail(principal.getName()).get();
        Question question = questionService.getById(id).get();
        voteQuestionService.upVoteQuestion(user, question);
        return voteQuestionService.getSumVoteQuestion(question);
    }

    @ApiOperation(value = "Добавить 'отрицательный' голос за вопрос")
    @PostMapping("/{questionId}/downVote")
    public Long downVoteQuestion(@Parameter(description = "id вопроса, за который идет голосование", required = true)
                                     @PathVariable(value = "questionId") Long id, Principal principal) {
        User user = userService.getByEmail(principal.getName()).get();
        Question question = questionService.getById(id).get();
        voteQuestionService.downVoteQuestion(user, question);
        return voteQuestionService.getSumVoteQuestion(question);
    }
}
