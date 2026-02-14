package com.example.university.dto;

import com.example.university.model.Lesson;

import java.util.List;

public class CourseResponse {
    public Long id;
    public String title;
    public Integer credits;
    public List<Lesson> lessons;
}
