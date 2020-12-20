package com.example.demo.controller;

import com.example.demo.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Ahmed Marzook"),
            new Student(2, "Ammaar Murshid"),
            new Student(3, "Arham Murshid"),
            new Student(4, "Hassan Marzook")
    );

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents(){
        System.out.println("Got All Students");
        return STUDENTS;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<?> registerNewStudent(@RequestBody Student student) {
        System.out.println(student.toString() + " Registered new Student");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println(studentId + " " + student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
