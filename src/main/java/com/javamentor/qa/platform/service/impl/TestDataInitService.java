package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {

    @PersistenceContext
    private EntityManager entityManager;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public TestDataInitService(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Transactional
    public void createEntity() {
        Role role_admin = new Role("ROLE_ADMIN");
        Role role_user = new Role("ROLE_USER");
        User admin = new User(
                1L,
                "admin@mail.ru",
                "admin",
                "admin",
                LocalDateTime.now(),
                true,
                false,
                "City",
                "https://www.google.ru",
                "https://github.com",
                "https://vk.com",
                "About",
                "image link",
                LocalDateTime.now(),
                "admin",
                role_admin);

        entityManager.persist(role_admin);
        entityManager.persist(role_user);
        entityManager.persist(admin);
    }

    @Transactional
    public void createTags() {
        Tag java = new Tag(
                1L,
                "Java",
                "Java (не путать с JavaScript) — строго типизированный объектно-ориентированный язык программирования. Приложения Java обычно транслируются в специальный байт-код, поэтому они могут работать на любой компьютерной архитектуре, с помощью виртуальной Java-машины (JVM). Используйте эту метку для вопросов, относящихся к языку Java или инструментам из платформы Java.",
                LocalDateTime.now(),
                new ArrayList<Question>());
        Tag javascript = new Tag(
                2L,
                "Javascript",
                "JavaScript (не путать с Java) — динамический, интерпретируемый язык со слабой типизацией, обычно используемый для написания скриптов на стороне клиента. Эта метка предназначена для вопросов, связанных с ECMAScript, его различными диалектами и реализациями (за исключением ActionScript). Если нет меток, относящихся к фреймворкам, предполагается, что код в ответах также не должен требовать сторонних библиотек.",
                LocalDateTime.now(),
                new ArrayList<Question>());
        Tag python = new Tag(
                3L,
                "Python",
                "Python — высокоуровневый язык с динамической типизацией, ориентированный на создание легко читаемого кода. Имеет две основные версии — 2 и 3. Используйте дополнительную метку [python-2.x] или [python-3.x], если ваш вопрос ориентирован на конкретную версию.",
                LocalDateTime.now(),
                new ArrayList<Question>());

        entityManager.persist(java);
        entityManager.persist(javascript);
        entityManager.persist(python);
    }

    @Transactional
    public void createVotes() {
        Role roleUserOne = entityManager
                .createQuery("SELECT r FROM Role r WHERE r.name = 'ROLE_USER'", Role.class)
                .getSingleResult();

        User userOne = new User(
                2L,
                "userOne@mail.ru",
                "userOne",
                "userOne",
                LocalDateTime.now(),
                true,
                false,
                "cityOne",
                "https://www.userone.ru",
                "https://github.com",
                "https://vk.com",
                "aboutUserOne",
                "imageUserOne link",
                LocalDateTime.now(),
                "userOne",
                roleUserOne);

        Question questionOne = new Question(1L,
                "questionOne",
                "descriptionQuestionOne",
                LocalDateTime.now(),
                userOne,
                new ArrayList<Tag>(),
                LocalDateTime.now(),
                false,
                new ArrayList<Answer>(),
                new ArrayList<CommentQuestion>(),
                new ArrayList<UserFavoriteQuestion>(),
                new ArrayList<VoteQuestion>());

        VoteQuestion voteQuestion = new VoteQuestion(
                1L,
                userOne,
                questionOne,
                LocalDateTime.now(),
                VoteTypeQ.UP);

        entityManager.persist(userOne);
        entityManager.persist(questionOne);
        entityManager.persist(voteQuestion);
    }

    public void createQuestions(User user, List<Tag> tags, int count) {

        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Question question = new Question();
            question.setTitle(user.getNickname() + "'s question");
            question.setDescription("Asked by " + user.getFullName());
            question.setTags(tags);
            question.setUser(user);
            questions.add(question);
        }

        questionService.persistAll(questions);
    }

    public void createAnswers(User answerer, List<Question> questions) {

        List<Answer> answers = new ArrayList<>();
        for (Question question : questions) {
            Answer answer = new Answer();
            answer.setUser(answerer);
            answer.setQuestion(question);
            answer.setHtmlBody("Answer to " + question.getTitle());
            answer.setIsHelpful(false);
            answer.setIsDeleted(false);
            answer.setIsDeletedByModerator(false);

            answers.add(answer);
        }

        answerService.persistAll(answers);
    }
}


