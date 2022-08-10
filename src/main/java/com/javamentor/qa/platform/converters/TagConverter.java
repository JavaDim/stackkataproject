package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.question.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagConverter {

    TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

    TagDto toDto(Tag tag);

}
