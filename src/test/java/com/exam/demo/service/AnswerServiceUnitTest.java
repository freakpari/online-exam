package com.exam.demo.service;

import com.exam.demo.entity.User;
import com.exam.demo.model.*;
import com.exam.demo.repo.AnswerRepository;
import com.exam.demo.repo.QuestionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerServiceUnitTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionsRepository questionsRepository;

    @InjectMocks
    private AnswerServiceImpl answerService;

    private MultipleChoiceQuestion mcq;
    private DescriptiveQuestion descQuestion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mcq = new MultipleChoiceQuestion();
        mcq.setId(1);
        mcq.setQuestionText("MCQ Sample");
        mcq.setCorrectOptionIndex(2);

        descQuestion = new DescriptiveQuestion();
        descQuestion.setId(2);
        descQuestion.setQuestionText("Describe something");
    }

    @Test
    void testSubmitAnswer_MCQ() {
        when(questionsRepository.findById(1)).thenReturn(Optional.of(mcq));
        when(answerRepository.save(any(McAnswer.class))).thenAnswer(i -> i.getArgument(0));

        Answer answer = answerService.submitAnswer(10, 1, 2);

        assertTrue(answer instanceof McAnswer);
        assertEquals(10, ((McAnswer) answer).getStudent().getId());
        assertEquals(2, ((McAnswer) answer).getSelectedOptionIndex());
    }

    @Test
    void testSubmitAnswer_Descriptive() {
        when(questionsRepository.findById(2)).thenReturn(Optional.of(descQuestion));
        when(answerRepository.save(any(DescriptiveAnswer.class))).thenAnswer(i -> i.getArgument(0));

        Answer answer = answerService.submitAnswer(11, 2, "My descriptive answer");

        assertTrue(answer instanceof DescriptiveAnswer);
        assertEquals("My descriptive answer", ((DescriptiveAnswer) answer).getAnswerText());
        assertFalse(answer.isCorrected());
    }

    @Test
    void testGradeDescriptiveAnswer() {
        DescriptiveAnswer ans = new DescriptiveAnswer();
        ans.setId(100);
        ans.setCorrected(false);

        when(answerRepository.findById(100)).thenReturn(Optional.of(ans));
        when(answerRepository.save(any(DescriptiveAnswer.class))).thenAnswer(i -> i.getArgument(0));

        Answer graded = answerService.gradeDescriptiveAnswer(100, 8.0);

        assertEquals(8.0, graded.getScore());
        assertTrue(graded.isCorrected());
    }
}
