package com.exam.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String courseDescription;

    @OneToMany(mappedBy = "course")
    private Set<CourseInstance> courseInstances;

}
