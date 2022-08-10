package com.javamentor.qa.platform.webapp.controllers.resource;

import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/user")
@Api(value = "User Controller")
public class ResourceUserController {

    private final UserDtoService userDtoService;

    public ResourceUserController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @ApiOperation(value = "Возвращает UserDto", notes = "Метод принимает User id и возвращает UserDto")
    @ApiParam(
            name = "id",
            type = "Long",
            value = "User id",
            example = "110",
            required = true
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запрос был успешно выполнен"),
            @ApiResponse(code = 400, message = "Ошибка на стороне Клиента"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable("id") Long id) throws Exception {

        Optional<UserDto> userDto = userDtoService.getUserDtoById(id);

        return userDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}