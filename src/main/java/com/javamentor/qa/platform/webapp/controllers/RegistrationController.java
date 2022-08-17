package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.MailService;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("api/user/registration")
public class RegistrationController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserConverter userConverter;

    private final MailService mailService;



    @Autowired
    public RegistrationController(UserService userService, UserConverter userConverter, RoleService roleService, MailService mailService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.roleService = roleService;
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@ModelAttribute @Valid UserRegistrationDto userRegistrationDto) {
        Optional<User> userOptional = userService.getByEmail(userRegistrationDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getIsEnabled()) {
                user.setLastUpdateDateTime(LocalDateTime.now());
                userService.update(user);
                mailService.sendMessage(user.getFullName(), user.getEmail().hashCode(), user.getEmail());
            }

            return new ResponseEntity<>("Аккаунт уже создан! Вам отправлено повторное письмо с подтверждением email",
                    HttpStatus.OK);
        }


        User user = userConverter.userRegistrationDtoToUser(userRegistrationDto);
        user.setRole(roleService.getByName("ROLE_USER").get());
        userService.persist(user);
        mailService.sendMessage(user.getFullName(), user.getEmail().hashCode(), user.getEmail());
        return new ResponseEntity<>("Аккаунт создан! Вам отправлено письмо с подтверждением email",
                HttpStatus.OK);
    }

    @GetMapping("verify")
    public ResponseEntity<String> verify(@RequestParam (name = "Id") long id, @RequestParam (name = "Hc")int code) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getIsEnabled()) {
                return new ResponseEntity<>("Вы уже зарегистрированы!", HttpStatus.OK);
            } else if (LocalDateTime.now().isAfter(user.getLastUpdateDateTime().plusMinutes(1))) {
                userService.delete(user);
                return new ResponseEntity<>("Время проверки email превышено, пройдите регистрацию снова.", HttpStatus.OK);
            } else if (user.getEmail().hashCode() == code) {
                user.setIsEnabled(true);
                userService.update(user);
                return new ResponseEntity<>("Вы успешно зарегистрировались!", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Пройдите регистрацию.", HttpStatus.OK);
    }
}