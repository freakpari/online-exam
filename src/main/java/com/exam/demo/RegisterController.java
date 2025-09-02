package com.exam.demo;

import com.exam.demo.dto.RegisterDto;
import com.exam.demo.dto.UserDto;
import com.exam.demo.model.User;
import com.exam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    private String saveUser (@ModelAttribute("userDto") RegisterDto userDto){
        userService.saveUser(userDto);
        return "redirect:/register";
    }
    @GetMapping
    public String showUserList(ModelMap model){
        List<User> users = userService.findAll();
        model.addAttribute("register", users);
        return "register";
    }
}
