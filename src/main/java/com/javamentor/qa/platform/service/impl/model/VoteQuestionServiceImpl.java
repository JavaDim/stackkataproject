package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteTypeQ;
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
        if (user == question.getUser()) {
            throw new VoteException("Пользователь не может голосовать за свой вопрос");
        }
        Optional<VoteQuestion> optionalVoteQuestion = voteQuestionDao.getByUserAndQuestion(user, question);
        if (optionalVoteQuestion.isEmpty()) {
            voteQuestionDao.persist(new VoteQuestion(user, question, VoteTypeQ.UP));
        } else {
            VoteQuestion voteQuestion = optionalVoteQuestion.get();
            if (voteQuestion.getVoteTypeQ() == VoteTypeQ.UP) {
                throw new VoteException("Ранее пользователь проголосовал 'за'");
            } else {
                voteQuestion.setVoteTypeQ(VoteTypeQ.UP);
                voteQuestionDao.update(voteQuestion);
            }
        }
        Optional<Reputation> reputationOptional = reputationDao.getByAuthorAndSenderAndQuestionAndType(question.getUser(), user, question, ReputationType.VoteQuestion);
        if (reputationOptional.isPresent()) {
            Reputation reputation = reputationOptional.get();
            reputation.setCount(reputation.getCount() + 10);
            reputationDao.update(reputation);
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
        if (user == question.getUser()) {
            throw new VoteException("Пользователь не может голосовать за свой вопрос");
        }
        Optional<VoteQuestion> optionalVoteQuestion = voteQuestionDao.getByUserAndQuestion(user, question);
        if (optionalVoteQuestion.isEmpty()) {
            voteQuestionDao.persist(new VoteQuestion(user, question, VoteTypeQ.DOWN));
        } else {
            VoteQuestion voteQuestion = optionalVoteQuestion.get();
            if (voteQuestion.getVoteTypeQ() == VoteTypeQ.DOWN) {
                throw new VoteException("Ранее пользователь проголосовал 'против'");
            } else {
                voteQuestion.setVoteTypeQ(VoteTypeQ.DOWN);
                voteQuestionDao.update(voteQuestion);
            }
        }
        Optional<Reputation> reputationOptional = reputationDao.getByAuthorAndSenderAndQuestionAndType(question.getUser(), user, question, ReputationType.VoteQuestion);
        if (reputationOptional.isPresent()) {
            Reputation reputation = reputationOptional.get();
            reputation.setCount(reputation.getCount() - 5);
            reputationDao.update(reputation);
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
