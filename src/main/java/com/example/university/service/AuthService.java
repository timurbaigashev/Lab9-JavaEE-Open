package com.example.university.service;

import com.example.university.dto.RegisterRequest;
import com.example.university.dto.RegistrationRequest;
import com.example.university.model.Session;
import com.example.university.model.Student;
import com.example.university.repository.StudentRepository;
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
    private final StudentRepository repo;
    private final EmailService mailService; // 1. Добавили MailService

    @Autowired
    public AuthService(JdbcTemplate jdbcTemplate, MongoTemplate mongoTemplate,
                       JwtProvider jwtProvider, BCryptPasswordEncoder passwordEncoder,
                       StudentRepository repo, EmailService mailService) { // 2. Внедряем через конструктор
        this.jdbcTemplate = jdbcTemplate;
        this.mongoTemplate = mongoTemplate;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.repo = repo;
        this.mailService = mailService;
    }

    public void register(RegistrationRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already taken");
        }

        String encodedPassword = passwordEncoder.encode(req.getPassword());

        String verificationCode = String.valueOf((int)((Math.random() * 900000) + 100000));

        Student s = new Student();
        s.setFirstName(req.getFirstName());
        s.setLastName(req.getLastName());
        s.setEmail(req.getEmail());
        s.setAge(req.getAge());
        s.setPassword(encodedPassword);

        s.setVerificationCode(verificationCode);

        repo.insert(s);

        mailService.sendVerificationCode(req.getEmail(), verificationCode);
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