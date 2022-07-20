package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;


public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    @PersistenceContext
    private EntityManager entityManager;

    private final VoteAnswerDao voteAnswerDao;
    private final ReputationDao reputationDao;

    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao, ReputationDao reputationDao) {
        super(voteAnswerDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationDao = reputationDao;
    }

    @Override
    public Long vote(Answer answer, User user, Integer repCount, VoteType voteType) {
        Optional<VoteAnswer> voteAnswerFromDb = voteAnswerDao.getByAnswerIdAndUserId(answer.getId(), user.getId());
        Optional<Reputation> reputationFromDb = reputationDao.getByAnswerIdSenderId(answer.getId(), user.getId());

        VoteAnswer voteAnswer;
        Reputation reputation = new Reputation();

        if (voteAnswerFromDb.isPresent() && reputationFromDb.isPresent()) {
            voteAnswer = new VoteAnswer();
            voteAnswer.setId(voteAnswerFromDb.get().getId());
            voteAnswer.setUser(voteAnswerFromDb.get().getUser());
            voteAnswer.setAnswer(voteAnswerFromDb.get().getAnswer());
            voteAnswer.setPersistDateTime(LocalDateTime.now());
            voteAnswer.setVoteType(voteType);
            entityManager.merge(voteAnswer);
            reputation.setId(reputationFromDb.get().getId());
            reputation.setPersistDate(reputationFromDb.get().getPersistDate());
            reputation.setAuthor(reputationFromDb.get().getAuthor());
            reputation.setSender(reputationFromDb.get().getSender());
            reputation.setCount(repCount);
            reputation.setType(ReputationType.Answer);
            reputation.setQuestion(reputationFromDb.get().getQuestion());
            reputation.setAnswer(reputationFromDb.get().getAnswer());
            entityManager.merge(reputation);
        } else {
            voteAnswer = new VoteAnswer(user,answer,voteType);
            entityManager.persist(voteAnswer);
            reputation.setPersistDate(LocalDateTime.now());
            reputation.setAuthor(answer.getUser());
            reputation.setSender(user);
            reputation.setCount(repCount);
            reputation.setType(ReputationType.Answer);
            reputation.setQuestion(answer.getQuestion());
            reputation.setAnswer(answer);
            entityManager.persist(reputation);
        }
        return voteAnswerDao.sumVote(answer.getId());
    }
}
