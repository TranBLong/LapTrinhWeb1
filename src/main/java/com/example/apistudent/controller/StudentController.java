package com.example.apistudent.controller;

import com.example.apistudent.model.Student;
import com.example.apistudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String listStudents(Model model) {

        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students"; // students.html
    }
}
