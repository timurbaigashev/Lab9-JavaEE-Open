package com.example.university.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class StudentCreateRequest {
    public String firstName;
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
}
