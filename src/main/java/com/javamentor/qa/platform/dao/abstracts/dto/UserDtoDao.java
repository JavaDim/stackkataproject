package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.user.UserDto;

import java.util.Optional;

public interface UserDtoDao {
    Optional<UserDto> getUserDtoById(Long id);
}
