package com.example.university.dto;

public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName; // Добавь поля, которые тебе нужны
    private String lastName;
    private Integer age;

    // Пустой конструктор (обязателен для Jackson)
    public RegistrationRequest() {}

    public RegistrationRequest(String email, String password, String firstName, String lastName, Integer age) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Геттеры и сеттеры (ОБЯЗАТЕЛЬНО, иначе Spring не сможет прочитать JSON)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getAge() {return age; }
    public void setAge(Integer age) { this.age = age; }
}