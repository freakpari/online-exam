package com.exam.demo.service;

import com.exam.demo.dto.RegisterDto;
import com.exam.demo.dto.UserDto;
import com.exam.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void saveUser(RegisterDto user);
}
