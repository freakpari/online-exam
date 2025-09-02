package com.exam.demo.service;

import com.exam.demo.dto.StudentCoursesDTO;
import com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.repo.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Map<Integer, List<StudentEnrollmentDTO>> getStudentsGroupedByCourse(Integer teacherId) {
        List<StudentEnrollmentDTO> list = teacherRepository.findStudentsByTeacherId(teacherId);

        return list.stream()
                .collect(Collectors.groupingBy(StudentEnrollmentDTO::getCourseInstanceId));
    }


    public List<StudentCoursesDTO> getCoursesByStudent(Integer studentId) {
        return teacherRepository.findCoursesByStudentId(studentId);
    }


}
