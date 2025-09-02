package com.exam.demo.repo;

import com.exam.demo.dto.StudentCoursesDTO;
import com.exam.demo.dto.StudentEnrollmentDTO;
import com.exam.demo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



    public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

        @Query("""
        SELECT new com.exam.demo.dto.StudentEnrollmentDTO(
            t.id,
            p_t.nameFamily,
            c.courseName,
            ci.id,
            ci.schedule,
            s.id,
            p_s.nameFamily,
            p_s.phone,
            e.enrollmentDate
        )
        FROM Teacher t
        JOIN t.person p_t
        JOIN t.courseInstances ci
        JOIN ci.course c
        JOIN ci.enrollments e
        JOIN e.student s
        JOIN s.person p_s
        WHERE t.id = :teacherId
        ORDER BY ci.id, p_s.nameFamily
    """)
        List<StudentEnrollmentDTO> findStudentsByTeacherId(Integer teacherId);
        @Query("""
    SELECT new com.exam.demo.dto.StudentCoursesDTO(
        s.id,
        p_s.nameFamily,
        c.courseName,
        p_t.nameFamily,
        ci.schedule,
        e.enrollmentDate
    )
    FROM Student s
    JOIN s.person p_s
    JOIN s.enrollments e
    JOIN e.courseInstance ci
    JOIN ci.course c
    JOIN ci.teacher t
    JOIN t.person p_t
    WHERE s.id = :studentId
    ORDER BY e.enrollmentDate DESC
""")
        List<StudentCoursesDTO> findCoursesByStudentId(Integer studentId);

    }

