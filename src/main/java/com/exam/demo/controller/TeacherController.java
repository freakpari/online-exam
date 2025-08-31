package com.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

@Controller
public class TeacherController {

    @GetMapping("/teacher/home")
    public String teacherHome(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("role", "TEACHER");
        return "teacher/home";
    }
}
