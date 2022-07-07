package com.javamentor.qa.platform.service.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Tag;

import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {
    private TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public Optional<List<Tag>> getTagsByNames(List<String> names) {
        return tagDao.getTagsByNames(names);
    }
}
