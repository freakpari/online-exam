package com.exam.demo.service;

import com.exam.demo.dto.StudentCoursesDTO;
import com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.model.CourseInstance;
import com.exam.demo.repo.CourseInstanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseInstanceService {
    private final CourseInstanceRepository repository;

    public CourseInstanceService(CourseInstanceRepository repository) {
        this.repository = repository;
    }

    public List<CourseInstance> getCoursesByTeacher(Integer teacherId) {
        return repository.findByTeacherId(teacherId);
    }


    public List<CourseInstance> getCoursesByStudent(Integer studentId) {
        return repository.findByStudentId(studentId);
    }

}
