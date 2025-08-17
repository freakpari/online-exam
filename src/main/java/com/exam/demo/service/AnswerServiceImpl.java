package com.exam.demo.service;

import com.exam.demo.entity.User;
import com.exam.demo.model.*;
import com.exam.demo.repo.AnswerRepository;
import com.exam.demo.repo.QuestionsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionsRepository questionsRepository;

    @Override
    @Transactional
    public Answer submitAnswer(Integer studentId, Integer questionId, Object answerPayload) {
        Questions question = questionsRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Answer answer;

        if (question instanceof MultipleChoiceQuestion mcq) {
            McAnswer mcAnswer = new McAnswer();
            mcAnswer.setStudent(new User(studentId));
            mcAnswer.setQuestion(mcq);
            mcAnswer.setSelectedOptionIndex((int) answerPayload);
            mcAnswer.calculateScore();
            answer = mcAnswer;
        } else {
            DescriptiveAnswer descAnswer = new DescriptiveAnswer();
            descAnswer.setStudent(new User(studentId));
            descAnswer.setQuestion(question);
            descAnswer.setAnswerText((String) answerPayload);
            descAnswer.setCorrected(false);
            answer = descAnswer;
        }

        return answerRepository.save(answer);
    }

    @Override
    @Transactional
    public Answer gradeDescriptiveAnswer(Integer answerId, Double score) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (answer instanceof DescriptiveAnswer) {
            answer.setScore(score);
            answer.setCorrected(true);
        } else {
            throw new RuntimeException("Only descriptive answers can be graded manually.");
        }

        return answerRepository.save(answer);
    }

    @Override
    public Double calculateExamScore(Integer studentId, Integer examId) {
        List<Answer> answers = answerRepository.findByStudentIdAndQuestionExamId(studentId, examId);
        return answers.stream()
                .filter(Answer::isCorrected)
                .mapToDouble(a -> a.getScore() != null ? a.getScore() : 0.0)
                .sum();
    }
}
