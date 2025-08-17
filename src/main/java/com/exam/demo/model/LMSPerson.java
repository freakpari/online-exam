package com.exam.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LMS_person")
@Getter
@Setter
@NoArgsConstructor
public class LMSPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nameFamily;

    @Column(length = 11, unique = true)
    private String phone;

    @Column(length = 10, unique = true)
    private String nationalCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
