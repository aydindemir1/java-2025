package com.aydindemir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aydindemir.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    boolean existsByEmail(String email);

    Optional<Teacher> findByEmail(String email);
}
