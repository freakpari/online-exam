package com.exam.demo.repository;

import com.exam.demo.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
    List<Questions> findByExamId(Integer examId);

    List<Questions> deleteByExamId(Integer examId);


}
