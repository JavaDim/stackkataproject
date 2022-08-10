package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserDtoService {
    Optional<UserDto> getUserDtoById(Long id);
}