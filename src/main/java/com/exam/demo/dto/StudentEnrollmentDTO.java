package com.exam.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentEnrollmentDTO {
    private Integer teacherId;
    private String teacherName;
    private String courseName;
    private Integer courseInstanceId;
    private String schedule;
    private Integer studentId;
    private String studentName;
    private String studentPhone;
    private LocalDate enrollmentDate;

    public StudentEnrollmentDTO(Integer teacherId, String teacherName, String courseName, Integer courseInstanceId, String schedule, Integer studentId, String studentName, String studentPhone, LocalDate enrollmentDate) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.courseInstanceId = courseInstanceId;
        this.schedule = schedule;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.enrollmentDate = enrollmentDate;
    }
}
