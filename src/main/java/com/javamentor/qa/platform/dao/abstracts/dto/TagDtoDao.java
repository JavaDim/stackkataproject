package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDtoDao {
    Optional<List<Tag>> getTop3TagByUserId(Long id);
}
