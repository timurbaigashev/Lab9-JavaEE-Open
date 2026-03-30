package com.example.university.service;

import com.example.university.dto.RegisterRequest;
import com.example.university.model.Session;
import com.example.university.model.Student;
import com.example.university.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class AuthService {
    private final JdbcTemplate jdbcTemplate;
    private final MongoTemplate mongoTemplate;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(JdbcTemplate jdbcTemplate, MongoTemplate mongoTemplate,
                       JwtProvider jwtProvider, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.mongoTemplate = mongoTemplate;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        // 1. Хешируем пароль перед сохранением в базу!
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 2. Сохраняем нового студента в PostgreSQL (или другую SQL базу) через JdbcTemplate
        String sql = "INSERT INTO students (first_name, last_name, email, password, age) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                encodedPassword,
                0 // возраст по умолчанию, если его нет в форме регистрации
        );
    }

    public String login(String email, String rawPassword) {
        String sql = "SELECT * FROM students WHERE email = ?";
        Student student;

        try {
            student = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Student.class), email);
        } catch (EmptyResultDataAccessException e) {
            // Если юзер не найден в SQL
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден");
        }

        if (student != null && passwordEncoder.matches(rawPassword, student.getPassword())) {
            String token = jwtProvider.generateToken(email);

            Session session = new Session();
            session.setStudentId(student.getId());
            session.setToken(token);
            session.setExpiryDate(Instant.now().plusSeconds(86400));

            mongoTemplate.save(session);
            return token;
        }

        // Если пароль не подошел
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный пароль");
    }
}