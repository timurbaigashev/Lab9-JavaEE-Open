package com.example.university.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private Set<String> roles = new HashSet<>();
    private Integer age;
    public OffsetDateTime createdAt;
    String verificationCode;


    public Student(String firstName, String lastName, String email, String password, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
    }


    public Student(Long id, String firstName, String lastName, String email, Integer age) {
    }

    public Student() {

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
    public void setAge(Integer age) { this.age = age; }
//    public Set<String> getRoles() { return roles; }
//    public void setRoles(Set<String> roles) { this.roles = roles; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public String getVerificationCode() { return  verificationCode; }
    public void setVerificationCode(String verificationCode) {
    }
}
