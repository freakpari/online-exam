package com.exam.demo.service;

import com.exam.demo.model.Answer;

public interface AnswerService {

    Answer submitAnswer(Integer studentId, Integer questionId, Object answerPayload);

    Answer gradeDescriptiveAnswer(Integer answerId, Double score);

    Double calculateExamScore(Integer studentId, Integer examId);
}
