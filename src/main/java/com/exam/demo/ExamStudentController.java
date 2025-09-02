package com.exam.demo;

import com.exam.demo.model.Exam;
import com.exam.demo.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student/exams")
public class ExamStudentController {
    private final ExamService examService;

    public ExamStudentController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/{courseInstanceId}")
    public String showAvailableExams(@PathVariable Integer courseInstanceId, Model model) {
        List<Exam> exams = examService.getAvailableExamsForStudent(courseInstanceId);
        model.addAttribute("exams", exams);
        return "student-exams";
    }
}
