package com.exam.demo.service;

import com.exam.demo.dto.DescriptiveQuestionDto;
import com.exam.demo.dto.MultipleChoiceQuestionDto;
import com.exam.demo.dto.QuestionsDto;
import com.exam.demo.model.DescriptiveQuestion;
import com.exam.demo.model.Exam;
import com.exam.demo.model.MultipleChoiceQuestion;
import com.exam.demo.model.Questions;
import com.exam.demo.repository.ExamRepository;
import com.exam.demo.repository.QuestionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionsServiceImpl(QuestionsRepository questionRepository, ExamRepository examRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }


    private QuestionsDto convertToDto(Questions question) {
        if (question instanceof MultipleChoiceQuestion mcq) {
            MultipleChoiceQuestionDto dto = new MultipleChoiceQuestionDto();
            dto.setQuestionText(mcq.getQuestionText());
            dto.setOptions(mcq.getOptions());
            dto.setCorrectOptionIndex(mcq.getCorrectOptionIndex() + 1);
            dto.setType("multipleChoice");
            dto.setExamId(mcq.getExam().getId());
            return dto;
        } else if (question instanceof DescriptiveQuestion dq) {
            DescriptiveQuestionDto dto = new DescriptiveQuestionDto();
            dto.setQuestionText(dq.getQuestionText());
            dto.setType("descriptive");
            dto.setExamId(dq.getExam().getId());
            return dto;
        } else {
            throw new IllegalArgumentException("Unknown question type");
        }
    }

    @Override
    public Questions addMultipleChoiceQuestion(MultipleChoiceQuestionDto dto) {
        Exam exam = examRepository.findById(dto.getExamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Exam not found"));

        if (dto.getOptions() == null || dto.getOptions().size() < 2 || dto.getOptions().size() > 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Options must be between 2 and 6");
        }

        if (dto.getCorrectOptionIndex() < 1 || dto.getCorrectOptionIndex() > dto.getOptions().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Correct option index is invalid");
        }

        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setExam(exam);
        question.setQuestionText(dto.getQuestionText());
        question.setOptions(dto.getOptions());
        question.setCorrectOptionIndex(dto.getCorrectOptionIndex() - 1);
        return questionRepository.save(question);
    }

    @Override
    public Questions addDescriptiveQuestion(DescriptiveQuestionDto dto) {
        Exam exam = examRepository.findById(dto.getExamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Exam not found"));

        DescriptiveQuestion question = new DescriptiveQuestion();
        question.setExam(exam);
        question.setQuestionText(dto.getQuestionText());
        return questionRepository.save(question);
    }

    public List<QuestionsDto> getQuestionsByExamId(Integer examId) {
        examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Exam not found with id: " + examId));
        List<Questions> questions = questionRepository.findByExamId(examId);

        return questions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
