package com.example.university.dto;

import jakarta.validation.constraints.*;

public class UserRegister {

    @NotBlank
    @Size(min = 3, max = 50)
    public String username;

    @NotBlank
    @Email
    @Size(max = 255)
    public String email;

    @NotBlank
    @Size(min = 8, max = 100)
    public String password;

    @NotNull
    @Min(16)
    public Integer age;
}