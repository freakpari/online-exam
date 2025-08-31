package com.exam.demo.controller;

import com.exam.demo.model.Answer;
import com.exam.demo.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/submit")
    public ResponseEntity<Answer> submitAnswer(
            @RequestParam Integer studentId,
            @RequestParam Integer questionId,
            @RequestBody Object answerPayload
    ) {
        Answer savedAnswer = answerService.submitAnswer(studentId, questionId, answerPayload);
        return ResponseEntity.ok(savedAnswer);
    }

    @PutMapping("/{answerId}/grade")
    public ResponseEntity<Answer> gradeAnswer(
            @PathVariable Integer answerId,
            @RequestParam Double score
    ) {
        Answer updatedAnswer = answerService.gradeDescriptiveAnswer(answerId, score);
        return ResponseEntity.ok(updatedAnswer);
    }

    @GetMapping("/exam/{examId}/student/{studentId}/score")
    public ResponseEntity<Double> getExamScore(
            @PathVariable Integer examId,
            @PathVariable Integer studentId
    ) {
        Double totalScore = answerService.calculateExamScore(studentId, examId);
        return ResponseEntity.ok(totalScore);
    }
}