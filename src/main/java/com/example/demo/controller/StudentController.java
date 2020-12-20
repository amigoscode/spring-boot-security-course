package com.example.demo.controller;

import com.example.demo.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Ahmed Marzook"),
            new Student(2, "Ammaar Murshid"),
            new Student(3, "Arham Murshid"),
            new Student(4, "Hassan Marzook")
    );

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId())).findFirst().orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exist"));
    }

    @PostMapping
    public ResponseEntity<?> addNewStudent() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
