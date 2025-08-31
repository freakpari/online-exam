package com.exam.demo.repo;

import com.exam.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    /*List<Student> findByPerson_NameContainingIgnoreCase(String  nameFamily);*/

}
