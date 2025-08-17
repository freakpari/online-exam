package com.exam.demo.service;

import com.exam.demo.date.JalaliDateConverter;
import com.exam.demo.dto.ExamDto;
import com.exam.demo.model.CourseInstance;
import com.exam.demo.model.Exam;
import com.exam.demo.repo.CourseInstanceRepository;
import com.exam.demo.repo.ExamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService{

    private final ExamRepository examRepository;
    private final CourseInstanceRepository courseInstanceRepository;
    private final JalaliDateConverter converter = new JalaliDateConverter();

    public ExamServiceImpl(ExamRepository examRepository, CourseInstanceRepository courseInstanceRepository) {
        this.examRepository = examRepository;
        this.courseInstanceRepository = courseInstanceRepository;
    }

    public LocalDate parseJalaliToLocalDate(String jalaliDateStr) {
        String[] parts = jalaliDateStr.split("/");
        int jy = Integer.parseInt(parts[0]);
        int jm = Integer.parseInt(parts[1]);
        int jd = Integer.parseInt(parts[2]);
        return converter.jalaliToGregorian(jy, jm, jd);
    }
    public String convertLocalDateToJalali(LocalDate date) {
        int[] jalali = converter.gregorianToJalali(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        return String.format("%04d/%02d/%02d", jalali[0], jalali[1], jalali[2]);
    }

    private Exam toEntity(ExamDto dto, int courseInstanceId) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setExamDate(parseJalaliToLocalDate(dto.getExamDate()));
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setPublishAt(dto.getPublishAt());
        exam.setDeleted(false);

        CourseInstance courseInstance = courseInstanceRepository.findById(courseInstanceId)
                .orElseThrow(() ->
                 new RuntimeException("CourseInstance not found with id: " + courseInstanceId));
        exam.setCourseInstance(courseInstance);

        return exam;
    }
    private ExamDto toDto(Exam exam) {
        ExamDto dto = new ExamDto();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setExamDate(convertLocalDateToJalali(exam.getExamDate()));
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setPublishAt(exam.getPublishAt());
        dto.setCourseInstanceId(exam.getCourseInstance().getId());
        return dto;
    }

    public ExamDto createExam(ExamDto examDto, int courseInstanceId) {
        LocalDate examDate = parseJalaliToLocalDate(examDto.getExamDate());
        if (examDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The exam date cannot be before today.");
        }
        Exam exam = toEntity(examDto, courseInstanceId);
        Exam savedExam = examRepository.save(exam);
        return toDto(savedExam);
    }

    public ExamDto updateExamById(int id, ExamDto examDto) {
        LocalDate examDate = parseJalaliToLocalDate(examDto.getExamDate());
        if (examDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The exam date cannot be before today.");
        }
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
        existingExam.setTitle(examDto.getTitle());
        existingExam.setDescription(examDto.getDescription());
        existingExam.setExamDate(examDate);
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
