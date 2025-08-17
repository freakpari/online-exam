package com.exam.demo.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleChoiceQuestionDto extends QuestionsDto {

    @Size(min = 2, max = 6, message = "تعداد گزینه‌ها باید بین 2 تا 6 باشد")
    private List<@NotBlank(message = "گزینه نباید خالی باشد") String> options;

    @Min(value = 1, message = "ایندکس جواب درست باید از 1 شروع شود")
    private int correctOptionIndex;
}
