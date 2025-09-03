package com.exam.demo.repo;

import com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Integer> {
    List<CourseInstance> findByTeacherId(Integer teacherId);

    List<CourseInstance> findByEnrollmentsStudentId(Integer studentId);


}


