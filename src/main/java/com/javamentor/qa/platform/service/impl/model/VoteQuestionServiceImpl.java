package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long>
        implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;

    @Autowired
    public VoteQuestionServiceImpl(VoteQuestionDao voteQuestionDao) {
        super(voteQuestionDao);
        this.voteQuestionDao = voteQuestionDao;
    }

    @Override
    public Optional<VoteQuestion> getByUserAndQuestion(User user, Question question) {
        return voteQuestionDao.getByUserAndQuestion(user, question);
    }

    @Override
    public Long getSumVoteQuestion(Question question) {
        return voteQuestionDao.getSumVoteQuestion(question);
    }
}
