package com.exam.demo.service;

import com.exam.demo.dto.ExamDto;
import com.exam.demo.model.CourseInstance;
import com.exam.demo.model.Exam;
import com.exam.demo.repository.CourseInstanceRepository;
import com.exam.demo.repository.ExamRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final CourseInstanceRepository courseInstanceRepository;

    public ExamService(ExamRepository examRepository, CourseInstanceRepository courseInstanceRepository) {
        this.examRepository = examRepository;
        this.courseInstanceRepository = courseInstanceRepository;
    }

    private Exam toEntity(ExamDto dto, int courseInstanceId) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setExamDate(dto.getExamDate());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setPublishAt(dto.getPublishAt());
        exam.setCreatedAt(LocalDateTime.now());
        exam.setDeleted(false);

        CourseInstance courseInstance = courseInstanceRepository.findById(courseInstanceId)
                .orElseThrow(() -> new RuntimeException("CourseInstance not found"));
        exam.setCourseInstance(courseInstance);
        return exam;
    }

    private ExamDto toDto(Exam exam) {
        ExamDto dto = new ExamDto();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setExamDate(exam.getExamDate());
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setPublishAt(exam.getPublishAt());
        dto.setCourseInstanceId(exam.getCourseInstance().getId());
        return dto;
    }

    public ExamDto createExam(ExamDto examDto, int courseInstanceId) {
        Exam exam = toEntity(examDto, courseInstanceId);
        Exam savedExam = examRepository.save(exam);
        return toDto(savedExam);
    }

    public ExamDto updateExamByTitle(String title, ExamDto examDto) {
        Exam existingExam = examRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Exam not found with title: " + title));

        existingExam.setDescription(examDto.getDescription());
        existingExam.setExamDate(examDto.getExamDate());
        existingExam.setStartTime(examDto.getStartTime());
        existingExam.setEndTime(examDto.getEndTime());
        existingExam.setPublishAt(examDto.getPublishAt());
        existingExam.setUpdatedAt(LocalDateTime.now());
        Exam updatedExam = examRepository.save(existingExam);
        return toDto(updatedExam);
    }

    public void deleteExamById(Integer id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }

    public List<ExamDto> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ExamDto getExamByTitle(String title) {
        Exam exam = examRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Exam not found with title: " + title));
        return toDto(exam);
    }
}
