package com.example.university.dto;

import com.example.university.model.Course;
import com.example.university.model.StudentProfile;

import java.time.OffsetDateTime;
import java.util.List;

public class StudentResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public Integer age;
    public OffsetDateTime createdAt;

    public StudentProfile profile;
    public List<Course> courses;
    public static class StudentProfileDto {
        public Long id;
        public String address;
        public String phone;
        public java.time.LocalDate birthDate;
    }
    public static class CourseDto {
        public Long id;
        public String title;
        public Integer credits;
    }
}
