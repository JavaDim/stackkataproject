package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;


import java.util.Optional;

public class TrackedTagServiceImpl  implements TrackedTagService {

    private TrackedTagDao trackedTagDao;

    public TrackedTagServiceImpl(TrackedTagDao trackedTagDao) {
        this.trackedTagDao = trackedTagDao;
    }

    @Override
    public Optional<Question> getUserAndTag(User user, Tag tag) {
        return trackedTagDao.getByUserAndTag(user, tag);
    }
}

