package com.exam.demo.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ExamDto {
    private int id;

    @NotNull
    private String title;

    private String description;

    private String examDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDateTime publishAt;

    @NotNull
    private Integer courseInstanceId;
}
