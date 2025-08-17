package com.exam.demo.repo;

import com.exam.demo.model.RoleType;
import com.exam.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByRole(RoleType role); // جستجو یک نقش
    List<UserRole> findAllByRole(RoleType role);
}
