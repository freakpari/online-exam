package com.exam.demo.controller;

import com.exam.demo.model.DescriptiveAnswer;
import com.exam.demo.model.McAnswer;
import com.exam.demo.service.AnswerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AnswerControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerController answerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private McAnswer mcAnswer;
    private DescriptiveAnswer descAnswer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();

        mcAnswer = new McAnswer();
        mcAnswer.setId(1);
        mcAnswer.setSelectedOptionIndex(2);
        mcAnswer.setScore(1.0);

        descAnswer = new DescriptiveAnswer();
        descAnswer.setId(2);
        descAnswer.setAnswerText("تشریحی جواب");
        descAnswer.setScore(5.0);
        descAnswer.setCorrected(true);
    }

    @Test
    void testSubmitMcAnswer() throws Exception {
        Mockito.when(answerService.submitAnswer(anyInt(), anyInt(), any()))
                .thenReturn(mcAnswer);

        mockMvc.perform(post("/answers/submit")
                        .param("studentId", "1")
                        .param("questionId", "10")
                        .content(objectMapper.writeValueAsString(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mcAnswer.getId()))
                .andExpect(jsonPath("$.selectedOptionIndex").value(mcAnswer.getSelectedOptionIndex()));
    }

    @Test
    void testSubmitDescriptiveAnswer() throws Exception {
        Mockito.when(answerService.submitAnswer(anyInt(), anyInt(), any()))
                .thenReturn(descAnswer);

        mockMvc.perform(post("/answers/submit")
                        .param("studentId", "1")
                        .param("questionId", "20")
                        .content(objectMapper.writeValueAsString("تشریحی جواب"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(descAnswer.getId()))
                .andExpect(jsonPath("$.answerText").value(descAnswer.getAnswerText()));
    }

    @Test
    void testGradeDescriptiveAnswer() throws Exception {
        Mockito.when(answerService.gradeDescriptiveAnswer(anyInt(), anyDouble()))
                .thenReturn(descAnswer);

        mockMvc.perform(put("/answers/2/grade")
                        .param("score", "5.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(descAnswer.getId()))
                .andExpect(jsonPath("$.score").value(descAnswer.getScore()))
                .andExpect(jsonPath("$.corrected").value(true));
    }

    @Test
    void testGetExamScore() throws Exception {
        Mockito.when(answerService.calculateExamScore(anyInt(), anyInt()))
                .thenReturn(6.0);

        mockMvc.perform(get("/answers/exam/100/student/1/score"))
                .andExpect(status().isOk())
                .andExpect(content().string("6.0"));
    }
}
