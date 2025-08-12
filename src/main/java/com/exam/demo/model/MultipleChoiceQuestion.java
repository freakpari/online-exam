package com.exam.demo.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MultipleChoiceQuestion extends Questions {

    @ElementCollection
    private List<String> options;

    private int correctOptionIndex;
}

