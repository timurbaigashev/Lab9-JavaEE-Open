package com.example.university.dto;

import com.example.university.model.Course;
import com.example.university.model.StudentProfile;

import java.util.List;

public class StudentResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public StudentProfile profile;
    public List<Course> courses;
}
