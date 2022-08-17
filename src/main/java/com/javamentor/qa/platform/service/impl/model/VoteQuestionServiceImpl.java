package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long>
        implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    private final ReputationDao reputationDao;

    @Autowired
    public VoteQuestionServiceImpl(VoteQuestionDao voteQuestionDao, ReputationDao reputationDao) {
        super(voteQuestionDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationDao = reputationDao;
    }

    @Override
    public Optional<VoteQuestion> getByUserAndQuestion(User user, Question question) {
        return voteQuestionDao.getByUserAndQuestion(user, question);
    }

    @Override
    public Long getSumVoteQuestion(Question question) {
        return voteQuestionDao.getSumVoteQuestion(question);
    }

    @Transactional
    @Override
    public void upVoteQuestion(User user, Question question) {
        voteQuestionDao.upVoteQuestion(user, question);
        Optional<Reputation> optionalReputation = reputationDao.getByAuthorAndSenderAndQuestionAndType(question.getUser(), user, question, ReputationType.VoteQuestion);
        if (optionalReputation.isPresent()) {
            reputationDao.updateToUpCountReputation(optionalReputation.get());
        } else {
            Reputation reputation = new Reputation();
            reputation.setQuestion(question);
            reputation.setAuthor(question.getUser());
            reputation.setSender(user);
            reputation.setType(ReputationType.VoteQuestion);
            reputation.setCount(10);
            reputationDao.persist(reputation);
        }
    }

    @Transactional
    @Override
    public void downVoteQuestion(User user, Question question) {
        voteQuestionDao.downVoteQuestion(user, question);
        Optional<Reputation> optionalReputation = reputationDao.getByAuthorAndSenderAndQuestionAndType(question.getUser(), user, question, ReputationType.VoteQuestion);
        if (optionalReputation.isPresent()) {
            reputationDao.updateToDownCountReputation(optionalReputation.get());
        } else {
            Reputation reputation = new Reputation();
            reputation.setQuestion(question);
            reputation.setAuthor(question.getUser());
            reputation.setSender(user);
            reputation.setType(ReputationType.VoteQuestion);
            reputation.setCount(-5);
            reputationDao.persist(reputation);
        }
    }
}
