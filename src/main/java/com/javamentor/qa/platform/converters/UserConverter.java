package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(source = "imageLink", target = "linkImage")
    @Mapping(source = "persistDateTime", target = "registrationDate")
    UserDto toDto(User user);

}
