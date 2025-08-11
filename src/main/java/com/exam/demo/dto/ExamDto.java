package com.exam.demo.dto;
import com.exam.demo.model.CourseInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExamDto {
    private int id;
    private String title;
    private String description;
    private String examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime publishAt;
    private int courseInstanceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getPublishAt() {
        return publishAt;
    }

    public int getCourseInstanceId() {
        return courseInstanceId;
    }

    public void setCourseInstanceId(int courseInstanceId) {
        this.courseInstanceId = courseInstanceId;
    }

    public void setPublishAt(LocalDateTime publishAt) {
        this.publishAt = publishAt;
    }

}
