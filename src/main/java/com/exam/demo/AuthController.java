package com.exam.demo;

import com.exam.demo.model.CourseInstance;
import com.exam.demo.model.Enrollment;
import com.exam.demo.model.RoleType;
import com.exam.demo.model.User;
import com.exam.demo.repo.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Integer id, Authentication authentication, Model model) {

        String loggedInUsername = authentication.getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));


        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));


        boolean isOwner = loggedInUser.getId().equals(user.getId());
        boolean isTeacher = loggedInUser.getRoles().stream()
                .anyMatch(r -> r.getRole() == RoleType.TEACHER);

        boolean isStudent = loggedInUser.getRoles().stream()
                .anyMatch(r -> r.getRole() == RoleType.STUDENT);


        System.out.println("isTeacher=" + isTeacher + ", isStudent=" + isStudent);


        if (!isOwner && !isTeacher) {
            return "error/403";
        }


        model.addAttribute("username", user.getUsername());
        model.addAttribute("isTeacher", isTeacher);
        model.addAttribute("isStudent", isStudent);

        if (isTeacher && user.getLmsPerson() != null && user.getLmsPerson().getTeacher() != null)
        {
            model.addAttribute("teacherId", user.getLmsPerson().getTeacher().getId());
        }
        if (isStudent && user.getLmsPerson() != null && user.getLmsPerson().getStudent() != null)
        {
            Integer studentId = user.getLmsPerson().getStudent().getId();
            model.addAttribute("studentId", studentId);
            Set<Enrollment> enrollments = user.getLmsPerson().getStudent().getEnrollments();
            if (!enrollments.isEmpty())
            {
                List<Map<String, Object>> courses = enrollments.stream().map(enrollment ->
            {
                Map<String, Object> map = new HashMap<>();
                CourseInstance ci = enrollment.getCourseInstance();
                map.put("courseInstanceId", ci.getId());
                map.put("courseName", ci.getCourse().getCourseName());
                return map;
            }).toList();
                 model.addAttribute("courses", courses);
            } }
        return "profile";
    }


    @GetMapping("/my-profile")
    public String myProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return "redirect:/profile/" + user.getId();
    }
}
