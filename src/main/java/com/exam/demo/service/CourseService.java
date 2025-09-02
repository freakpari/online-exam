package com.exam.demo.service;

import com.exam.demo.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> findCourse(Integer Id);
}
