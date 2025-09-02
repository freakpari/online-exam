package com.exam.demo.service;

import com.exam.demo.model.Course;
import com.exam.demo.repo.CourseRepository;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findCourse(Integer Id) {
        return null;
    }
}
