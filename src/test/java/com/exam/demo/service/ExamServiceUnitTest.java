package com.exam.demo.service;


import com.exam.demo.dto.ExamDto;
import com.exam.demo.model.CourseInstance;
import com.exam.demo.model.Exam;
import com.exam.demo.repo.CourseInstanceRepository;
import com.exam.demo.repo.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class ExamServiceUnitTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private CourseInstanceRepository courseInstanceRepository;

    @InjectMocks
    private ExamServiceImpl examService;

    private ExamDto validExamDto;
    private CourseInstance courseInstance;
    private Exam savedExam;

    @BeforeEach
    void setUp(){
        validExamDto = new ExamDto();
        validExamDto.setTitle("میانترم ریاضی عمومی");
        validExamDto.setDescription("ازمون ترم پاییز");
        validExamDto.setExamDate("1404/05/29");
        validExamDto.setPublishAt(LocalDateTime.now());
        validExamDto.setStartTime(LocalTime.of(9,0));
        validExamDto.setEndTime(LocalTime.of(11,0));

        courseInstance = new CourseInstance();
        courseInstance.setId(1);

        savedExam=new Exam();
        savedExam.setId(1);
        savedExam.setTitle("میانترم ریاضی عمومی");
        validExamDto.setDescription("ازمون ترم پاییز");
        savedExam.setExamDate(LocalDate.of(2025, 8, 20));
        savedExam.setStartTime(LocalTime.of(9,0));
        savedExam.setEndTime(LocalTime.of(11,0));
        savedExam.setCourseInstance(courseInstance);
    }

    @Test
    void parseJalaliToLocalDate_ValidInput_ReturnsCorrectDate(){
        LocalDate result = examService.parseJalaliToLocalDate("1402/10/15");
        assertEquals(LocalDate.of(2024, 1, 5), result);
    }

    @Test
    void convertLocalDateToJalali_ValidInput_ReturnsCorrectString(){
        String result = examService.convertLocalDateToJalali(LocalDate.of(2024, 1, 5));
        assertEquals("1402/10/15", result);
    }

    @Test
    void createExam_ValidInput_ReturnsExamDto(){
        when(courseInstanceRepository.findById(1)).thenReturn(Optional.of(courseInstance));
        when(examRepository.save(any(Exam.class))).thenReturn(savedExam);

        ExamDto result = examService.createExam(validExamDto,1);

        assertNotNull(result);
        assertEquals("میانترم ریاضی عمومی",result.getTitle());
        assertEquals("1404/05/29", result.getExamDate());
        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test
    void createExam_PastDate_ThrowsException(){
        ExamDto pastDateDto = new ExamDto();
        pastDateDto.setExamDate("1400/01/01");

        assertThrows(IllegalArgumentException.class, () -> {
            examService.createExam(pastDateDto, 1);
        });
    }

    @Test
    void createExam_CourseNotFound_ThrowsException() {
        when(courseInstanceRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class , () -> {
            examService.createExam(validExamDto,1);
        });
        }

    @Test
    void updateExamByTitle_ValidInput_ReturnsUpdatedExamDto(){
        ExamDto updateDto = new ExamDto();
        updateDto.setTitle("ریاضی آپدیت");
        updateDto.setDescription("اپدیت");
        updateDto.setExamDate("1404/05/22");
        updateDto.setStartTime(LocalTime.of(10,0));
        updateDto.setEndTime(LocalTime.of(12,0));

        Exam exitingExam = new Exam();
        exitingExam.setId(1);
        exitingExam.setTitle("ریاضی");
        exitingExam.setDescription("توضیح اصلی");
        exitingExam.setStartTime(LocalTime.of(9,0));
        exitingExam.setExamDate(LocalDate.of(2025,8,13));
        exitingExam.setCourseInstance(courseInstance);

        when(examRepository.findById(1)).thenReturn(Optional.of(exitingExam));
        when(examRepository.save(any(Exam.class))).thenReturn(exitingExam);

        ExamDto result = examService.updateExamById(1, updateDto);

        assertEquals("ریاضی آپدیت", result.getTitle());
        assertEquals("اپدیت", result.getDescription());
        assertEquals("1404/05/22", result.getExamDate());
    }

    @Test
    void deleteExamById_ExistingId_DeletesExam() {
        when(examRepository.existsById(1)).thenReturn(true);

        examService.deleteExamById(1);

        verify(examRepository,times(1)).deleteById(1);
    }

    @Test
    void deleteExamById_NonExistingId_ThrowsException() {
        when(examRepository.existsById(1)).thenReturn(false);

        assertThrows(RuntimeException.class,() -> {
            examService.deleteExamById(1);
        });
    }
    @Test
    void getAllExams_ReturnsListOfExamDtos() {

        when(examRepository.findAll()).thenReturn(List.of(savedExam));

        List<ExamDto> result = examService.getAllExams();

        assertEquals(1,result.size());
        assertEquals("میانترم ریاضی عمومی", result.get(0).getTitle());
    }
    @Test
    void getExamByTitle_ExistingTitle_ReturnsExamDto() {

        when(examRepository.findByTitle("میانترم ریاضی عمومی")).thenReturn(Optional.of(savedExam));

        ExamDto result = examService.getExamByTitle("میانترم ریاضی عمومی");

        assertEquals("میانترم ریاضی عمومی", result.getTitle());
    }
    @Test
    void getExamByTitle_NonExistingTitle_ThrowsException() {

        when(examRepository.findByTitle("میانترم")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            examService.getExamByTitle("میانترم");
        });
    }

}
