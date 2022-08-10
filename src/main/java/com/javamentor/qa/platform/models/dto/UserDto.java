package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String fullName;

    private String linkImage;

    private String city;

    private Long reputation;

    private LocalDateTime registrationDate;

    private Long votes;

//    TODO: после добавления инициализации тэгов
//    private List<TagDto> listTop3TagDto;

}
