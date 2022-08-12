package com.javamentor.qa.platform.models.dto.question.answer;

import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDto implements Serializable {
    @ApiModelProperty(notes = "ID ответа", example = "1")
    private Long id;
    @ApiModelProperty(notes = "ID вопроса", example = "1")
    private Long questionId;
    @ApiModelProperty(notes = "ID автора ответа", example = "1")
    private Long userId;
    @ApiModelProperty(notes = "Дата создания ответа", example = "2022-08-02T13:05:54")
    private LocalDateTime persistDateTime;
    @ApiModelProperty(notes = "Ссылка на аватарку автора", example = "http://kata.com/avatar.jpg")
    private String userImageLink;
    @ApiModelProperty(notes = "Никнейм автора", example = "kataAcademy")
    private String userNickname;
    @ApiModelProperty(notes = "Текст ответа", example = "Текст ответа представлен в виде строки")
    @NotNull
    private String htmlBody;
    @ApiModelProperty(notes = "Польза ответа", example = "true")
    @NotNull
    private Boolean isHelpful;
    @ApiModelProperty(notes = "Дата решения вопроса", example = "2022-08-02T13:05:54")
    private LocalDateTime dateAcceptTime;
    @ApiModelProperty(notes = "Количество голосов", example = "1")
    private Long voteCount;
    @ApiModelProperty(notes = "Рейтинг ответа", example = "1")
    private Long ratingAnswer;
    @ApiModelProperty(notes = "Тип голоса", example = "UP")
    private VoteType voteType;
    @ApiModelProperty(notes = "Рейтинг юзера", example = "1")
    private Long countUserReputation;
}
