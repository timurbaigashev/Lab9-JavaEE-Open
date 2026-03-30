package com.example.university.model;

import org.jspecify.annotations.Nullable;

import java.time.OffsetDateTime;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    public OffsetDateTime createdAt;

    public Student() {}
    public Student(Long id, String firstName, String lastName, String email, int age) {
        this.id = id; this.firstName = firstName; this.lastName = lastName; this.email = email; this.age = age;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() {return age; }
    public void setAge(int age) {this.age=age; }

    public OffsetDateTime getCreatedAt(){
        return createdAt;
    }
}
