package com.example.apistudent.service;

import com.example.apistudent.model.Student;
import com.example.apistudent.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(int id) {
        Optional<Student> student = repository.findById(id);
        return student.orElse(null);
    }

    public List<Student> searchStudentsByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
