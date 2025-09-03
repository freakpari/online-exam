package com.exam.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    private String username;
    private String password;
    private String nameFamily;
    private String phone;
    private String nationalCode;
}
