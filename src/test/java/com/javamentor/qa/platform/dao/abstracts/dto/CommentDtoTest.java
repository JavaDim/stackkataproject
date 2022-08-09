package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentDtoTest {
    @Mock
    private CommentDtoDao dtoDao;

    private CommentDtoService dtoService;

    @BeforeEach
    void initUseCase() {
        dtoService = new CommentDtoServiceImpl(dtoDao);
    }

    @Test
    public void testGetAllQuestionCommentDtoById() {
        QuestionCommentDto commentDto = new QuestionCommentDto();
        commentDto.setQuestionId(1L);
        commentDto.setText("bla bla");
        List<QuestionCommentDto> commentDtoFromDb = Arrays.asList(commentDto);
        when(dtoDao.getAllQuestionCommentDtoById(1L)).thenReturn(commentDtoFromDb);
        List<QuestionCommentDto> comments = dtoService.getAllQuestionCommentDtoById(1L);
        assertEquals(1, comments.size());
        assertEquals("bla bla", comments.get(0).getText());
    }

    @Test
    public void testQuestionCommentsIsEmpty() {
        Question question = new Question();
        question.setId(1L);
        when(dtoDao.getAllQuestionCommentDtoById(1L)).thenReturn(null);
        List<QuestionCommentDto> comments = dtoService.getAllQuestionCommentDtoById(1L);
        Assert.assertEquals(null, comments);
    }
}