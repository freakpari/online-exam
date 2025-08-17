package com.exam.demo.model;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class McAnswer extends Answer {

    private int selectedOptionIndex;

    public boolean isCorrect() {
        if (getQuestion() instanceof MultipleChoiceQuestion mcq) {
            return mcq.getCorrectOptionIndex() == selectedOptionIndex;
        }
        return false;
    }

    public void calculateScore() {
        if (isCorrect()) {
            setScore(getQuestion().getScore());
        } else {
            setScore(0.0);
        }
        setCorrected(true);
    }


}
