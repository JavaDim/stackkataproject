package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public abstract class UserConverter {

    @Mapping(target = "fullName", source = ".", qualifiedByName="toFullName")
    public abstract User userRegistrationDtoToUser (UserRegistrationDto userRegistrationDto);

    @Named("toFullName")
    protected String translateToFullName(UserRegistrationDto userRegistrationDto) {
        return userRegistrationDto.getFirstName() + " " + userRegistrationDto.getLastName();
    }

    @Mapping(target = "firstName", source = ".", qualifiedByName="toFirstName")
    @Mapping(target = "lastName", source = ".", qualifiedByName="toLastName")
    public abstract UserRegistrationDto userToUserRegistrationDto (User user);
//
//
    @Named("toFirstName")
    protected String translateToFirstName(User user) {
        String[] name = user.getFullName().split(" ");
        return name[0];
    }

    @Named("toLastName")
    protected String translateLastName(User user) {
        String[] name = user.getFullName().split(" ");
        return name[1];
    }
}
