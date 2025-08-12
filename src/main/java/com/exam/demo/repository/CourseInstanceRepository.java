package com.exam.demo.repository;

import com.exam.demo.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Integer> {
}


