package com.exam.demo.repo;

import com.exam.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    /*List<Student> findByPerson_NameContainingIgnoreCase(String  nameFamily);*/
 /*   List<Student> findStudentByEnrollments();*/
    @Query("""
        SELECT s.id 
        FROM Student s 
        JOIN s.person lp 
        JOIN lp.user u 
        WHERE u.username = :username
    """)
    Integer findIdByUsername(@Param("username") String username);

}
