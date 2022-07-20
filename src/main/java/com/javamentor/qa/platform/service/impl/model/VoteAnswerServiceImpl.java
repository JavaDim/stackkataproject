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

import java.time.LocalDateTime;
import java.util.Optional;

public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final VoteAnswerDao voteAnswerDao;
    private final ReputationDao reputationDao;

    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao, ReputationDao reputationDao) {
        super(voteAnswerDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationDao = reputationDao;
    }

    private Long vote(Answer answer, User user, int repCount, VoteType voteType) {
        Optional<VoteAnswer> voteAnswerOptional = voteAnswerDao.getByAnswerIdAndUserId(answer.getId(), user.getId());
        Optional<Reputation> reputationOptional = reputationDao.getByAnswerIdSenderId(answer.getId(), user.getId());
        VoteAnswer voteAnswer;
        Reputation reputation;
        if (voteAnswerOptional.isPresent() && reputationOptional.isPresent()) {
            voteAnswer = voteAnswerOptional.get();
            reputation = reputationOptional.get();
        } else {
            voteAnswer = new VoteAnswer();
            reputation = new Reputation();
        }
        voteAnswer.setVoteType(voteType);
        voteAnswer.setAnswer(answer);
        voteAnswer.setUser(user);
        voteAnswer.setPersistDateTime(LocalDateTime.now());

        reputation.setAnswer(answer);
        reputation.setAuthor(answer.getUser());
        reputation.setCount(repCount);
        reputation.setSender(user);
        reputation.setType(ReputationType.Answer);
        try {
            voteAnswerDao.update(voteAnswer);
            reputationDao.update(reputation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voteAnswerDao.sumVote(answer.getId());
    }
}
