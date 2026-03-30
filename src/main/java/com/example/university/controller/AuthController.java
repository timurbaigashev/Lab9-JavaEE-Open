package com.example.university.controller;

import com.example.university.dto.AuthResponse;
import com.example.university.dto.LoginRequest;
import com.example.university.dto.RegisterRequest;
import com.example.university.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // Внедряем AuthService
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Передаем email и пароль в сервис, получаем обратно сгенерированный JWT
        String token = authService.login(request.getEmail(), request.getPassword());

        // Возвращаем JSON вида { "token": "eyJh..." }
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        // Вызываем метод регистрации в сервисе
        authService.register(request);

        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }
}