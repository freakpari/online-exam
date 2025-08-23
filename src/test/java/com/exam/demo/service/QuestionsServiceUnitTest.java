package com.exam.demo.service;

import com.exam.demo.dto.DescriptiveQuestionDto;
import com.exam.demo.dto.MultipleChoiceQuestionDto;
import com.exam.demo.dto.QuestionsDto;
import com.exam.demo.model.DescriptiveQuestion;
import com.exam.demo.model.Exam;
import com.exam.demo.model.MultipleChoiceQuestion;
import com.exam.demo.model.Questions;
import com.exam.demo.repo.ExamRepository;
import com.exam.demo.repo.QuestionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionsServiceUnitTest {

    @Mock
    private QuestionsRepository questionRepository;

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private QuestionsServiceImpl questionsService;

    private Exam testExam;
    private MultipleChoiceQuestionDto mcqDto;
    private DescriptiveQuestionDto descDto;

    @BeforeEach
    void setUp() {
        testExam = new Exam();
        testExam.setId(1);

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
    }

    @Test
    void addMultipleChoiceQuestion_ValidInput_ReturnsQuestion() {
        when(examRepository.findById(1)).thenReturn(Optional.of(testExam));
        when(questionRepository.save(any(MultipleChoiceQuestion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Questions result = questionsService.addMultipleChoiceQuestion(mcqDto);

        assertNotNull(result);
        assertTrue(result instanceof MultipleChoiceQuestion);
        MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) result;
        assertEquals("What is 2+2?", mcq.getQuestionText());
        assertEquals(1, mcq.getCorrectOptionIndex());
        assertEquals(3, mcq.getOptions().size());
        verify(questionRepository, times(1)).save(any(MultipleChoiceQuestion.class));
    }

    @Test
    void addMultipleChoiceQuestion_InvalidOptions_ThrowsException() {
        mcqDto.setOptions(Arrays.asList("only one option"));

        assertThrows(ResponseStatusException.class,
                () -> questionsService.addMultipleChoiceQuestion(mcqDto));
    }

    @Test
    void addMultipleChoiceQuestion_InvalidCorrectIndex_ThrowsException() {
        mcqDto.setCorrectOptionIndex(5);

        assertThrows(ResponseStatusException.class,
                () -> questionsService.addMultipleChoiceQuestion(mcqDto));
    }

    @Test
    void addMultipleChoiceQuestion_ExamNotFound_ThrowsException() {
        when(examRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> questionsService.addMultipleChoiceQuestion(mcqDto));
    }

    @Test
    void addDescriptiveQuestion_ValidInput_ReturnsQuestion() {
        when(examRepository.findById(1)).thenReturn(Optional.of(testExam));
        when(questionRepository.save(any(DescriptiveQuestion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Questions result = questionsService.addDescriptiveQuestion(descDto);

        assertNotNull(result);
        assertTrue(result instanceof DescriptiveQuestion);
        assertEquals("Explain the theory of relativity", result.getQuestionText());
        verify(questionRepository, times(1)).save(any(DescriptiveQuestion.class));
    }

    @Test
    void getQuestionsByExamId_ValidExam_ReturnsQuestions() {
        when(examRepository.findById(1)).thenReturn(Optional.of(testExam));

        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setQuestionText("MCQ 1");
        mcq.setExam(testExam);

        DescriptiveQuestion dq = new DescriptiveQuestion();
        dq.setQuestionText("DQ 1");
        dq.setExam(testExam);

        when(questionRepository.findByExamId(1)).thenReturn(Arrays.asList(mcq, dq));

        List<QuestionsDto> result = questionsService.getQuestionsByExamId(1);

        assertEquals(2, result.size());
        assertEquals("MCQ 1", result.get(0).getQuestionText());
        assertEquals("DQ 1", result.get(1).getQuestionText());
    }

    @Test
    void updateQuestionText_ValidId_UpdatesQuestion() {
        Questions question = new DescriptiveQuestion();
        question.setQuestionText("Old text");
        when(questionRepository.findById(1)).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Questions.class))).thenReturn(question);

        Questions result = questionsService.updateQuestionText(1, "New text");

        assertEquals("New text", result.getQuestionText());
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void updateQuestionText_QuestionNotFound_ThrowsException() {
        when(questionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> questionsService.updateQuestionText(1, "New text"));
    }

    @Test
    void updateMultipleChoiceQuestion_ValidInput_UpdatesQuestion() {
        MultipleChoiceQuestion existing = new MultipleChoiceQuestion();
        existing.setQuestionText("Old question");
        existing.setOptions(Arrays.asList("A", "B", "C"));
        existing.setCorrectOptionIndex(0);

        when(questionRepository.findById(1)).thenReturn(Optional.of(existing));
        when(questionRepository.save(any(MultipleChoiceQuestion.class))).thenReturn(existing);

        MultipleChoiceQuestionDto dto = new MultipleChoiceQuestionDto();
        dto.setQuestionText("Updated question");
        dto.setOptions(Arrays.asList("X", "Y", "Z"));
        dto.setCorrectOptionIndex(2);

        Questions result = questionsService.updateMultipleChoiceQuestion(1, dto);

        assertEquals("Updated question", result.getQuestionText());
        assertEquals(1, ((MultipleChoiceQuestion) result).getCorrectOptionIndex()); // converted from 2 to 1
        verify(questionRepository, times(1)).save(existing);
    }

    @Test
    void deleteQuestionById_ValidId_DeletesQuestion() {
        when(questionRepository.existsById(1)).thenReturn(true);

        questionsService.deleteQuestionById(1);

        verify(questionRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteQuestionById_QuestionNotFound_ThrowsException() {
        when(questionRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> questionsService.deleteQuestionById(1));
    }


    @Test
    void deleteAllQuestionsByExamId_ValidExam_ReturnsDeletedQuestions() {
        when(examRepository.findById(1)).thenReturn(Optional.of(testExam));

        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setQuestionText("Q1");
        mcq.setExam(testExam);

        when(questionRepository.deleteByExamId(1)).thenReturn(Arrays.asList(mcq));

        List<QuestionsDto> result = questionsService.deleteAllQuestionsByExamId(1);

        assertEquals(1, result.size());
        assertEquals("Q1", result.get(0).getQuestionText());
        verify(questionRepository, times(1)).deleteByExamId(1);
    }
    @Test
    void getQuestionsByExamId_ExamNotFound_ThrowsException() {
        when(examRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> questionsService.getQuestionsByExamId(1));
    }

    @Test
    void convertToDto_MultipleChoiceQuestion_ReturnsCorrectDto() {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setQuestionText("MCQ");
        mcq.setOptions(Arrays.asList("A", "B", "C"));
        mcq.setCorrectOptionIndex(1);
        mcq.setExam(testExam);

        QuestionsDto dto = questionsService.convertToDto(mcq);

        assertTrue(dto instanceof MultipleChoiceQuestionDto);
        MultipleChoiceQuestionDto mcqDto = (MultipleChoiceQuestionDto) dto;
        assertEquals("MCQ", mcqDto.getQuestionText());
        assertEquals(2, mcqDto.getCorrectOptionIndex());
        assertEquals(3, mcqDto.getOptions().size());
    }

    @Test
    void convertToDto_DescriptiveQuestion_ReturnsCorrectDto() {
        DescriptiveQuestion dq = new DescriptiveQuestion();
        dq.setQuestionText("DQ");
        dq.setExam(testExam);

        QuestionsDto dto = questionsService.convertToDto(dq);

        assertTrue(dto instanceof DescriptiveQuestionDto);
        assertEquals("DQ", dto.getQuestionText());
        assertEquals("descriptive", dto.getType());
    }
    @Test
    void addDescriptiveQuestion_duplicate_shouldThrowConflict() {
        DescriptiveQuestionDto dto = new DescriptiveQuestionDto();
        dto.setQuestionText("Duplicate Question?");
        dto.setExamId(1);

        when(examRepository.findById(1)).thenReturn(Optional.of(new Exam()));
        when(questionRepository.existsByHash(any())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                questionsService.addDescriptiveQuestion(dto)
        );

        assertEquals(409, exception.getStatusCode().value());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void addDescriptiveQuestion_examNotFound_shouldThrowNotFound() {
        DescriptiveQuestionDto dto = new DescriptiveQuestionDto();
        dto.setQuestionText("Some Question?");
        dto.setExamId(1);

        when(examRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                questionsService.addDescriptiveQuestion(dto)
        );

        assertEquals(404, exception.getStatusCode().value());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void addMultipleChoiceQuestion_duplicate_shouldThrowConflict() {
        MultipleChoiceQuestionDto dto = new MultipleChoiceQuestionDto();
        dto.setQuestionText("Duplicate MCQ?");
        dto.setOptions(Arrays.asList("A", "B", "C"));
        dto.setCorrectOptionIndex(2);
        dto.setExamId(1);

        when(examRepository.findById(1)).thenReturn(Optional.of(new Exam()));
        when(questionRepository.existsByHash(any())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                questionsService.addMultipleChoiceQuestion(dto)
        );

        assertEquals(409, exception.getStatusCode().value());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void addMultipleChoiceQuestion_examNotFound_shouldThrowNotFound() {
        MultipleChoiceQuestionDto dto = new MultipleChoiceQuestionDto();
        dto.setQuestionText("Some MCQ?");
        dto.setOptions(Arrays.asList("A", "B", "C"));
        dto.setCorrectOptionIndex(2);
        dto.setExamId(1);

        when(examRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                questionsService.addMultipleChoiceQuestion(dto)
        );

        assertEquals(404, exception.getStatusCode().value());
        verify(questionRepository, never()).save(any());
    }
}