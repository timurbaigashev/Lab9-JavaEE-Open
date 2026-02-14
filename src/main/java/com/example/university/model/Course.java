package com.example.university.model;

public class Course {
    private Long id;
    private String title;
    private Integer credits;

    public Course() {}
    public Course(Long id, String title, Integer credits) {
        this.id = id; this.title = title; this.credits = credits;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
}
