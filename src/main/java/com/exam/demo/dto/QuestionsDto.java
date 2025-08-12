package com.exam.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class QuestionsDto {
    @NotBlank
    private String questionText;

    @NotNull
    private String type;

    @NotNull(message = "آیدی آزمون الزامی است")
    private Integer examId;
}
