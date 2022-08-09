package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCommentDto {
    private Long id;
    private Long questionId;
    private LocalDateTime lastRedactionDate;
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    private String text;
    @NotNull
    private Long userId;
    private String imageLink;
    private Long reputation;

    public QuestionCommentDto(Long id, Long questionId, LocalDateTime lastRedactionDate, LocalDateTime persistDate, String text, Long userId, String imageLink, int reputation) {
        this.id = id;
        this.questionId = questionId;
        this.lastRedactionDate = lastRedactionDate;
        this.persistDate = persistDate;
        this.text = text;
        this.userId = userId;
        this.imageLink = imageLink;
        this.reputation = Long.valueOf(reputation);
    }
}
