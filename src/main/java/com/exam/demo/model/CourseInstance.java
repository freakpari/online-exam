package com.exam.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "course_instance")
public class CourseInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int courseId;

    private int teacherId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(length = 100)
    private String schedule;
}