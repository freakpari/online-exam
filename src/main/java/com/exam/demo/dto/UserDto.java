package com.exam.demo.dto;


import com.exam.demo.model.LMSPerson;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    Integer id;
    String username;
    String password;
    LMSPerson lmsPerson;
}
