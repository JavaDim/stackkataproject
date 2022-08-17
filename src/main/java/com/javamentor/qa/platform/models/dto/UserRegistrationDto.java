package com.javamentor.qa.platform.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegistrationDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
