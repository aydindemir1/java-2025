package com.aydindemir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aydindemir.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
