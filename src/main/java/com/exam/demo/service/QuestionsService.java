package com.exam.demo.service;

import com.exam.demo.dto.DescriptiveQuestionDto;
import com.exam.demo.dto.MultipleChoiceQuestionDto;
import com.exam.demo.dto.QuestionsDto;
import com.exam.demo.model.Questions;

import java.util.List;

public interface QuestionsService {

    Questions addMultipleChoiceQuestion(MultipleChoiceQuestionDto dto);

    Questions addDescriptiveQuestion(DescriptiveQuestionDto dto);

    List<QuestionsDto> getQuestionsByExamId(Integer examId);

}
