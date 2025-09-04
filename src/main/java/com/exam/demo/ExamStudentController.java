package com.exam.demo;

import com.exam.demo.dto.QuestionsDto;
import com.exam.demo.model.Exam;
import com.exam.demo.service.ExamService;
import com.exam.demo.service.QuestionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student/exams")
public class ExamStudentController {
    private final ExamService examService;
    private final QuestionsService questionsService;
    public ExamStudentController(ExamService examService, QuestionsService questionsService) {
        this.examService = examService;
        this.questionsService = questionsService;
    }

    @GetMapping("/{courseInstanceId}")
    public String showAvailableExams(@PathVariable Integer courseInstanceId, Model model) {
        List<Exam> exams = examService.getAvailableExamsForStudent(courseInstanceId);
        model.addAttribute("exams", exams);
        return "student-exams";
    }
    @GetMapping("/{examId}/questions")
    public String getQuestionsByExamId(@PathVariable Integer examId, ModelMap model) {
        List<QuestionsDto> questions = questionsService.getQuestionsByExamId(examId);
        model.addAttribute("questions",questions);
        return "show-exam";
    }
}
