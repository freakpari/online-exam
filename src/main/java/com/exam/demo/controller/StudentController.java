package com.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

@Controller
public class StudentController {

    @GetMapping("/student/home")
    public String studentHome(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("role", "STUDENT");
        return "student/home";
    }
}
