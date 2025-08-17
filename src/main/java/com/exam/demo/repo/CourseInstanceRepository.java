package com.exam.demo.repo;

import com.exam.demo.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Integer> {
}


