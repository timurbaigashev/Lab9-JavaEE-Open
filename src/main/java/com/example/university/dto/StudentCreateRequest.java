package com.example.university.dto;

import java.time.LocalDate;

public class StudentCreateRequest {
    public String firstName;
    public String lastName;
    public String email;

    public Profile profile;

    public static class Profile {
        public String address;
        public String phone;
        public LocalDate birthDate;
    }
}
