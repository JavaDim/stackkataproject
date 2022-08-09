package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.model.AnswerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;

    public ResourceAnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }

    @GetMapping("{answerId}")
    public ResponseEntity<?> deleteByAnswerId(@PathVariable("answerId") Long answerId) {
        if (answerService.getById(answerId).isPresent()) {
            answerService.deleteById(answerId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}