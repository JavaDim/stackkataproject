package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//TODO: после добавления инициализации тэгов
@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @Override
    public Optional<List<Tag>> getTop3TagByUserId(Long id) {
        return Optional.empty();
    }
}
