package com.exam.demo.repo;

import com.exam.demo.dto.StudentCoursesDTO;
import com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Integer> {
    List<CourseInstance> findByTeacherId(Integer teacherId);
    @Query("""
        SELECT ci
        FROM CourseInstance ci
        JOIN ci.enrollments e
        JOIN e.student s
        WHERE s.id = :studentId
        ORDER BY ci.schedule
    """)
    List<CourseInstance> findByStudentId(@Param("studentId") Integer studentId);



}


