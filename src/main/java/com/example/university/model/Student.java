package com.example.university.model;

import org.jspecify.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
    private int age;
    public OffsetDateTime createdAt;

    public Student() {}
    public Student(String firstName, String lastName, String email, String password, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
    }


    public Student(long id, String firstName, String lastName, String email, int age) {
    }

    public Student(Long id, String firstName, String lastName, String email, String password, Set<String> roles, int age, OffsetDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.age = age;
        this.createdAt = createdAt;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
