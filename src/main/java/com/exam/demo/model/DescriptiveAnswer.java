package com.exam.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DescriptiveAnswer extends Answer {

    @Column(columnDefinition = "TEXT")
    private String answerText;

}
