package com.exam.demo.repo;

import com.exam.demo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository <Exam, Integer>{
    Optional<Exam> findByTitle(String title);


}

