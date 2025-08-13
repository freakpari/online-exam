package com.exam.demo.controller;

import com.exam.demo.dto.*;
import com.exam.demo.model.*;
import com.exam.demo.service.QuestionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionsControllerUnitTest {

    @Mock
    private QuestionsService questionService;

    @InjectMocks
    private QuestionsController questionsController;

    private MultipleChoiceQuestionDto mcqDto;
    private DescriptiveQuestionDto descDto;
    private UpdateQuestionTextDto updateTextDto;
    private Questions question;
    private QuestionsDto questionDto;

    @BeforeEach
    void setUp() {
        mcqDto = new MultipleChoiceQuestionDto();
        mcqDto.setQuestionText("What is 2+2?");
        mcqDto.setOptions(Arrays.asList("3", "4", "5"));
        mcqDto.setCorrectOptionIndex(2);
        mcqDto.setExamId(1);
        mcqDto.setType("multipleChoice");

        descDto = new DescriptiveQuestionDto();
        descDto.setQuestionText("Explain the theory of relativity");
        descDto.setExamId(1);
        descDto.setType("descriptive");

        updateTextDto = new UpdateQuestionTextDto();
        updateTextDto.setNewQuestionText("Updated question text");

        question = new MultipleChoiceQuestion();
        question.setId(1);
        question.setQuestionText("Sample question");

        questionDto = new MultipleChoiceQuestionDto();
        questionDto.setQuestionText("Sample question");
        questionDto.setType("multipleChoice");
    }

    @Test
    void createMultipleChoiceQuestion_ValidInput_ReturnsCreated() {
        when(questionService.addMultipleChoiceQuestion(any(MultipleChoiceQuestionDto.class)))
                .thenReturn(question);

        ResponseEntity<Questions> response = questionsController.createMultipleChoiceQuestion(mcqDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sample question", response.getBody().getQuestionText());
        verify(questionService, times(1)).addMultipleChoiceQuestion(any(MultipleChoiceQuestionDto.class));
    }

    @Test
    void createDescriptiveQuestion_ValidInput_ReturnsCreated() {
        when(questionService.addDescriptiveQuestion(any(DescriptiveQuestionDto.class)))
                .thenReturn(question);

        ResponseEntity<Questions> response = questionsController.createDescriptiveQuestion(descDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sample question", response.getBody().getQuestionText());
        verify(questionService, times(1)).addDescriptiveQuestion(any(DescriptiveQuestionDto.class));
    }

    @Test
    void getQuestionsByExamId_ValidExamId_ReturnsQuestions() {
        List<QuestionsDto> questions = Arrays.asList(questionDto);
        when(questionService.getQuestionsByExamId(anyInt())).thenReturn(questions);

        ResponseEntity<List<QuestionsDto>> response = questionsController.getQuestionsByExamId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Sample question", response.getBody().get(0).getQuestionText());
        verify(questionService, times(1)).getQuestionsByExamId(1);
    }

    @Test
    void updateQuestionText_ValidInput_ReturnsUpdatedQuestion() {
        when(questionService.updateQuestionText(anyInt(), anyString()))
                .thenReturn(question);

        ResponseEntity<Questions> response = questionsController.updateQuestionText(1, updateTextDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sample question", response.getBody().getQuestionText());
        verify(questionService, times(1)).updateQuestionText(1, "Updated question text");
    }

    @Test
    void updateMultipleChoiceQuestion_ValidInput_ReturnsUpdatedQuestion() {
        when(questionService.updateMultipleChoiceQuestion(anyInt(), any(MultipleChoiceQuestionDto.class)))
                .thenReturn(question);

        ResponseEntity<Questions> response = questionsController.updateMultipleChoiceQuestion(1, mcqDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sample question", response.getBody().getQuestionText());
        verify(questionService, times(1)).updateMultipleChoiceQuestion(1, mcqDto);
    }

    @Test
    void deleteQuestion_ValidId_ReturnsNoContent() {
        doNothing().when(questionService).deleteQuestionById(anyInt());

        ResponseEntity<Void> response = questionsController.deleteQuestion(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(questionService, times(1)).deleteQuestionById(1);
    }

    @Test
    void deleteAllQuestionsByExamId_ValidExamId_ReturnsDeletedQuestions() {
        List<QuestionsDto> questions = Arrays.asList(questionDto);
        when(questionService.deleteAllQuestionsByExamId(anyInt())).thenReturn(questions);

        ResponseEntity<List<QuestionsDto>> response = questionsController.DeleteAllQuestionsByExamId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Sample question", response.getBody().get(0).getQuestionText());
        verify(questionService, times(1)).deleteAllQuestionsByExamId(1);
    }
}