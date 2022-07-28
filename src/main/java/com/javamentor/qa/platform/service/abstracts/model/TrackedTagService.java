package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;


public interface TrackedTagService  {
    public Optional<Question> getUserAndTag(User user, Tag tag) ;
}
