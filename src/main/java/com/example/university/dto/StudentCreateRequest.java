package com.example.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class StudentCreateRequest {
    @JsonProperty("first_name")
    private String firstName;
    public String lastName;
    public String email;
    public Integer age;
    public OffsetDateTime createdAt;

    public Profile profile;

    public static class Profile {
        public Long id;
        public String address;
        public String phone;
        public java.time.LocalDate birthDate;
    }
    @JsonProperty("first_name")
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
