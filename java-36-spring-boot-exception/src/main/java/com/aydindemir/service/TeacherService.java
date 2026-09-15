package com.aydindemir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aydindemir.exception.ConflictException;
import com.aydindemir.exception.ResourceNotFoundException;
import com.aydindemir.model.Teacher;
import com.aydindemir.repository.TeacherRepository;

@Service
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() {

        return teacherRepository.findAll();
    }

    // READ BY ID
    @Transactional(readOnly = true)
    public Teacher getTeacher(String id) {

        return teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher bulunamadı. id: " + id
                        )
                );
    }

    // CREATE
    public Teacher addTeacher(Teacher teacher) {

        if (teacherRepository.existsByEmail(teacher.getEmail())) {

            throw new ConflictException(
                    "Bu email adresi zaten kayıtlı: "
                            + teacher.getEmail()
            );
        }

        return teacherRepository.save(teacher);
    }

    // UPDATE
    public Teacher updateTeacher(String id, Teacher teacher) {

        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Güncellenecek Teacher bulunamadı. id: " + id
                        )
                );

        // Email başka bir Teacher tarafından kullanılıyor mu?
        teacherRepository.findByEmail(teacher.getEmail())
                .filter(foundTeacher ->
                        !foundTeacher.getId().equals(id))
                .ifPresent(foundTeacher -> {
                    throw new ConflictException(
                            "Bu email adresi başka bir Teacher tarafından kullanılıyor: "
                                    + teacher.getEmail()
                    );
                });

        existingTeacher.setFirstName(teacher.getFirstName());
        existingTeacher.setLastName(teacher.getLastName());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setPhone(teacher.getPhone());

        return teacherRepository.save(existingTeacher);
    }

    // DELETE
    public void deleteTeacher(String id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Silinecek Teacher bulunamadı. id: " + id
                        )
                );

        teacherRepository.delete(teacher);
    }
}