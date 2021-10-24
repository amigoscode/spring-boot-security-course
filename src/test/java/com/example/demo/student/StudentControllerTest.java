package com.example.demo.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentControllerTest {

    @Test
    public void testGetStudent() {
        StudentController controller = new StudentController();
        Student student = controller.getStudent(1);
        Assertions.assertEquals("James Bond", student.getStudentName());
    }
}