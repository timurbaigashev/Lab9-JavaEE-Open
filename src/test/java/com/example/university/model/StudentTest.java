package com.example.university.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {

    @Test
    void shouldCreateStudent() {
        Student student = new Student();
        student.setFirstName("Timur");

        assertEquals("Timur", student.getFirstName());
    }
}