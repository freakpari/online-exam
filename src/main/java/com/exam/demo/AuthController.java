package com.exam.demo;

import com.exam.demo.entity.RoleType;
import com.exam.demo.entity.User;
import com.exam.demo.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.management.relation.Role;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.jsp
    }
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Integer id, Authentication authentication, Model model) {

        String loggedInUsername = authentication.getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // پیدا کردن پروفایل مورد نظر بر اساس ID
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // دسترسی: فقط صاحب پروفایل یا استاد/ادمین
        boolean isOwner = loggedInUser.getId().equals(user.getId());
        boolean isTeacher = loggedInUser.getRoles().stream()
                .anyMatch(r -> r.getRole() == RoleType.TEACHER);

        boolean isStudent = loggedInUser.getRoles().stream()
                .anyMatch(r -> r.getRole() == RoleType.STUDENT);

// چاپ برای تست
        System.out.println("isTeacher=" + isTeacher + ", isStudent=" + isStudent);


        if (!isOwner && !isTeacher) {
            return "error/403"; // دسترسی غیرمجاز
        }

        // اطلاعات پروفایل و نقش‌ها
        model.addAttribute("username", user.getUsername());
        model.addAttribute("isTeacher", isTeacher);
        model.addAttribute("isStudent", isStudent);

        return "profile"; // profile.jsp
    }


    @GetMapping("/my-profile")
    public String myProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return "redirect:/profile/" + user.getId();
    }
}
