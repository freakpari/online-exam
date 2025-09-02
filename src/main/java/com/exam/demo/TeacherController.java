package com.exam.demo;

import com.exam.demo.dto.StudentCoursesDTO;
import
        com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.service.TeacherService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class  TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/{id}/students")
    public String getStudentsByTeacher(@PathVariable Integer id, Model model) {
        Map<Integer, List<StudentEnrollmentDTO>> groupedStudents = teacherService.getStudentsGroupedByCourse(id);
        model.addAttribute("groupedStudents", groupedStudents);
        return "teacher-students";
    }
    @GetMapping("/student/{studentId}/courses")
    public String showStudentCourses(@PathVariable Integer studentId, ModelMap model) {
        // گرفتن دوره‌های دانشجو
        List<StudentCoursesDTO> courses = teacherService.getCoursesByStudent(studentId);

        // اضافه کردن studentId به مدل برای استفاده در JSP
        model.addAttribute("studentId", studentId);
        model.addAttribute("courses", courses);

        return "student-courses";
    }


}
