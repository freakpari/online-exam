package com.exam.demo.repo;

import com.exam.demo.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByStudentIdAndQuestionExamId(Integer studentId, Integer examId);
}