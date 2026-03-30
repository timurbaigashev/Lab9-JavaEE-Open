package com.example.university.repository;

import com.example.university.model.Session;
import com.example.university.model.Student;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    private final JdbcTemplate jdbcTemplate; // Для работы со студентами
    private final MongoTemplate mongoTemplate; // Для работы с сессиями

    public AuthRepository(JdbcTemplate jdbcTemplate, MongoTemplate mongoTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    // Пример сохранения сессии
    public void saveSession(Session session) {
        mongoTemplate.save(session);
    }

    // Пример поиска студента
    public Student findByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Student.class),
                email // Просто передаем значение напрямую
        );
    }
}
