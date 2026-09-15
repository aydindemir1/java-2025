package com.aydindemir.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aydindemir.model.Teacher;
import com.aydindemir.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // GET ALL
    // GET http://localhost:9095/api/v1/teachers
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {

        List<Teacher> teachers = teacherService.getAllTeachers();

        return ResponseEntity.ok(teachers);
    }

    // GET BY ID
    // GET http://localhost:9095/api/v1/teachers/1
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacher(
            @PathVariable String id) {

        Teacher teacher = teacherService.getTeacher(id);

        return ResponseEntity.ok(teacher);
    }

    // CREATE
    // POST http://localhost:9095/api/v1/teachers
    @PostMapping
    public ResponseEntity<Teacher> addTeacher(
            @Valid @RequestBody Teacher teacher) {

        Teacher createdTeacher =
                teacherService.addTeacher(teacher);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTeacher);
    }

    // UPDATE
    // PUT http://localhost:9095/api/v1/teachers/1
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(
            @PathVariable String id,
            @Valid @RequestBody Teacher teacher) {

        Teacher updatedTeacher =
                teacherService.updateTeacher(id, teacher);

        return ResponseEntity.ok(updatedTeacher);
    }

    // DELETE
    // DELETE http://localhost:9095/api/v1/teachers/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable String id) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.noContent().build();
    }
}