package com.exam.demo.controller;

import com.exam.demo.dto.ExamDto;
import com.exam.demo.service.ExamServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExamControllerUnitTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ExamServiceImpl examService;

    @InjectMocks
    private ExamController examController;

    private ExamDto examDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();

        examDto = new ExamDto();
        examDto.setId(1);
        examDto.setTitle("Midterm Exam");
        examDto.setDescription("Mathematics Midterm");
        examDto.setExamDate("1402/10/15");
        examDto.setStartTime(LocalTime.of(9, 0));
        examDto.setEndTime(LocalTime.of(11, 0));
        examDto.setPublishAt(LocalDateTime.now());
        examDto.setCourseInstanceId(1);
    }

    @Test
    void createExam_ShouldReturnCreatedExam() throws Exception {
        ExamDto examDto = new ExamDto();
        examDto.setId(1);
        examDto.setTitle("Midterm Exam");
        examDto.setCourseInstanceId(1);

        when(examService.createExam(any(ExamDto.class), anyInt()))
                .thenReturn(examDto);

        mockMvc.perform(post("/api/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Midterm Exam"))
                .andExpect(jsonPath("$.courseInstanceId").value(1));
    }


    @Test
    void updateExam_ShouldReturnUpdatedExam() throws Exception {
        ExamDto updatedDto = new ExamDto();
        updatedDto.setTitle("Updated Exam");
        updatedDto.setDescription("Updated Description");

        when(examService.updateExamById(anyInt(), any(ExamDto.class)))
                .thenReturn(updatedDto);

        mockMvc.perform(put("/api/exams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Exam"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }


    @Test
    void deleteExam_ShouldReturnNoContent() throws Exception {
        doNothing().when(examService).deleteExamById(anyInt());

        mockMvc.perform(delete("/api/exams/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllExams_ShouldReturnExamList() throws Exception {
        List<ExamDto> exams = Arrays.asList(examDto);
        when(examService.getAllExams()).thenReturn(exams);

        mockMvc.perform(get("/api/exams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Midterm Exam"));
    }

    @Test
    void getExamByTitle_ShouldReturnExam() throws Exception {
        when(examService.getExamByTitle(anyString())).thenReturn(examDto);

        mockMvc.perform(get("/api/exams/Midterm Exam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Midterm Exam"));
    }

    @Test
    void createExam_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        ExamDto invalidDto = new ExamDto();

        mockMvc.perform(post("/api/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getExamByTitle_ShouldReturnNotFound_WhenExamNotExists() throws Exception {
        when(examService.getExamByTitle(anyString()))
                .thenThrow(new RuntimeException("Exam not found"));
        mockMvc.perform(get("/api/exams/NonExisting"))
                .andExpect(status().isNotFound());
    }
}