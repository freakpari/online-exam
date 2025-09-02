package com.exam.demo.repo;

import com.exam.demo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository <Exam, Integer>{
    Optional<Exam> findByTitle(String title);

    List<Exam> findByCourseInstance_IdAndExamDateEqualsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Integer courseInstanceId,
            java.time.LocalDate examDate,
            java.time.LocalTime now1,
            java.time.LocalTime now2
    );
}

