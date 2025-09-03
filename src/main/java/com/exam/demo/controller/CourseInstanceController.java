package com.exam.demo.controller;

import com.exam.demo.dto.StudentCoursesDTO;
import com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.model.CourseInstance;
import com.exam.demo.model.Student;
import com.exam.demo.service.CourseInstanceService;
import com.exam.demo.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseInstanceController {
    private final CourseInstanceService service;
    private final StudentService studentService;

    public CourseInstanceController(CourseInstanceService service, StudentService studentService) {
        this.service = service;

        this.studentService = studentService;
    }



    @GetMapping("/teacher/{teacherId}")
    public String showTeacherCourses(@PathVariable Integer teacherId, ModelMap model) {
        List<CourseInstance> courses = service.getCoursesByTeacher(teacherId);
        model.addAttribute("courses", courses);
        return "teacher-courses";
    }
    @GetMapping("/student/{studentId}")
    public String showStudentCourses(@PathVariable Integer studentId, ModelMap model) {
        List<CourseInstance> courses = service.getCoursesByStudent(studentId);
        model.addAttribute("courses", courses);
        System.out.println(courses);
        return "student-courses";
    }




}

