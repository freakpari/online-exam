package com.exam.demo.service;

import com.exam.demo.dto.StudentCoursesDTO;
import com.exam.demo.dto.StudentEnrollmentDTO;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    Map<Integer, List<StudentEnrollmentDTO>> getStudentsGroupedByCourse(Integer teacherId);
    List<StudentCoursesDTO> getCoursesByStudent(Integer studentId);
}
