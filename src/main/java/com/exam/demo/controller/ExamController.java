package com.exam.demo.controller;

import com.exam.demo.dto.ExamDto;
import com.exam.demo.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {

        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<?> createExam(@Valid @RequestBody ExamDto examDto) {
        try {
            ExamDto savedExam = examService.createExam(examDto, examDto.getCourseInstanceId());
            return new ResponseEntity<>(savedExam, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDto> updateExam(@PathVariable int id, @RequestBody ExamDto examDto) {
        ExamDto updatedExam = examService.updateExamById(id, examDto);
        return ResponseEntity.ok(updatedExam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Integer id) {
        examService.deleteExamById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ExamDto>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{title}")
    public ResponseEntity<ExamDto> getExamByTitle(@PathVariable String title) {
        try {
            return ResponseEntity.ok(examService.getExamByTitle(title));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}