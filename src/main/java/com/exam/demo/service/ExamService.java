package com.exam.demo.service;

import com.exam.demo.dto.ExamDto;
import com.exam.demo.model.Exam;

import java.util.List;

public interface ExamService {

    ExamDto createExam(ExamDto examDto, int courseInstanceId);

    ExamDto updateExamById(int id, ExamDto examDto);

    void deleteExamById(Integer id);

    List<ExamDto> getAllExams();

    ExamDto getExamByTitle(String title);

    List<Exam> getAvailableExamsForStudent(Integer courseInstanceId);
}
