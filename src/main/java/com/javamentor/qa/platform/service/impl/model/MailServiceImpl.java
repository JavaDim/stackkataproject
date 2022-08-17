package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.service.abstracts.model.MailService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final UserService userService;
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void send(String toAddress, String subject, String message) {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(username);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject(subject);
            messageHelper.setText(message);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendMessage(String fullName, int code, String mail) {
        Long id = userService.getByEmail(mail).get().getId();
        String message = String.format("Hallo, %s \n" +
                        "Please, visit next link:" + " " +
                        "http://localhost:8080/api/user/registration/verify?Id=%s&Hc=%s",
                fullName, id, code);
        this.send(mail, "Activation", message);
    }
}
