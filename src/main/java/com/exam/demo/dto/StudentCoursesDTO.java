package com.exam.demo.dto;

import java.time.LocalDate;

public class StudentCoursesDTO {

    private Integer studentId;
    private String studentName;
    private String courseName;
    private String teacherName;
    private String schedule;
    private LocalDate enrollmentDate;

    public StudentCoursesDTO(Integer studentId, String studentName, String courseName, String teacherName, String schedule, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.schedule = schedule;
        this.enrollmentDate = enrollmentDate;
    }
}
