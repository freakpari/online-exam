package com.exam.demo.repo;

import com.exam.demo.model.LMSPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LmsPersonRepository extends JpaRepository<LMSPerson,Integer> {
}
