package com.exam.demo.controller;

import com.exam.demo.dto.DescriptiveQuestionDto;
import com.exam.demo.dto.MultipleChoiceQuestionDto;
import com.exam.demo.dto.QuestionsDto;
import com.exam.demo.dto.UpdateQuestionTextDto;
import com.exam.demo.model.Questions;
import com.exam.demo.service.QuestionsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    private final QuestionsService questionService;

    public QuestionsController(QuestionsService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/multiple-choice")
    public ResponseEntity<Questions> createMultipleChoiceQuestion(@Valid @RequestBody MultipleChoiceQuestionDto dto) {
        Questions question = questionService.addMultipleChoiceQuestion(dto);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @PostMapping("/descriptive")
    public ResponseEntity<Questions> createDescriptiveQuestion(@Valid @RequestBody DescriptiveQuestionDto dto) {
        Questions question = questionService.addDescriptiveQuestion(dto);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @GetMapping("/exams/{examId}")
    public ResponseEntity<List<QuestionsDto>> getQuestionsByExamId(@PathVariable Integer examId) {
        List<QuestionsDto> questions = questionService.getQuestionsByExamId(examId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}/QuestionText")
    public ResponseEntity<Questions> updateQuestionText(
            @PathVariable Integer id,
            @RequestBody UpdateQuestionTextDto dto) {

        Questions updated = questionService.updateQuestionText(id, dto.getNewQuestionText());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/multiple-choice")
    public ResponseEntity<Questions> updateMultipleChoiceQuestion(
            @PathVariable Integer id,
            @Valid @RequestBody MultipleChoiceQuestionDto dto) {

        Questions updated = questionService.updateMultipleChoiceQuestion(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/exams/{examId}")
    public ResponseEntity<List<QuestionsDto>> DeleteAllQuestionsByExamId(@PathVariable Integer examId) {
        List<QuestionsDto> questions = questionService.deleteAllQuestionsByExamId(examId);
        return ResponseEntity.ok(questions);
    }

}
