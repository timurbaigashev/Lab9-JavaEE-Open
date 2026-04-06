package com.example.university.repository;

import com.example.university.dto.StudentResponse;
import com.example.university.model.Student;
import static com.example.university.mapper.StudentRowMappers.STUDENT;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private final JdbcTemplate jdbc;


    public StudentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

//    public Student insert(Student s) {
//        String sql = """
//        INSERT INTO public.students(first_name, last_name, email, age, password, verification_code)
//        VALUES (?, ?, ?, ?, ?, ?)
//        RETURNING id
//    """;
//
//        Long id = jdbc.queryForObject(sql, Long.class,
//                s.getFirstName(),
//                s.getLastName(),
//                s.getEmail(),
//                s.getAge(),
//                s.getPassword(),
//                s.getVerificationCode()
//        );
//
//        s.setId(id);
//        return s;
//    }
public Student insert(Student s) {
    String sql = "INSERT INTO students (first_name, last_name, email, age, password, verification_code) " +
            "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

    // Используем queryForObject, чтобы получить сгенерированный ID обратно
    Integer generatedId = jdbc.queryForObject(sql, Integer.class,
            s.getFirstName(),
            s.getLastName(),
            s.getEmail(),
            s.getAge(),
            s.getPassword(),
            s.getVerificationCode()
    );

    s.setId(Long.valueOf(generatedId)); // Устанавливаем полученный ID в объект
    return s; // Возвращаем объект с заполненным ID
}

    public Long insertReturningId(Student s) {
        return jdbc.queryForObject("""
            INSERT INTO public.students(first_name,last_name,email,age)
            VALUES (?,?,?,?)
            RETURNING id
        """, Long.class, s.getFirstName(), s.getLastName(), s.getEmail(), s.getAge());
    }

    public Optional<Student> findById(Long id) {
        List<Student> list = jdbc.query("SELECT * FROM public.students WHERE id=?", STUDENT, id);
        return list.stream().findFirst();
    }

    public List<Student> findAll() {
        return jdbc.query("SELECT * FROM public.students ORDER BY id", STUDENT);
    }

    public long countAll() {
        Long cnt = jdbc.queryForObject("SELECT COUNT(1) FROM public.students", Long.class);
        return cnt == null ? 0L : cnt;
    }

    public List<Student> findPage(int page, int size, String sortBy, String dir) {
        int offset = page * size;

        String orderBy = switch (sortBy == null ? "id" : sortBy) {
            case "id" -> "id";
            case "firstName" -> "first_name";
            case "lastName" -> "last_name";
            case "email" -> "email";
            case "age" -> "age";
            default -> "id";
        };

        String direction = "DESC".equalsIgnoreCase(dir) ? "DESC" : "ASC";

        String sql = """
        SELECT *
        FROM public.students
        ORDER BY %s %s
        LIMIT ? OFFSET ?
    """.formatted(orderBy, direction);

        return jdbc.query(sql, STUDENT, size, offset);
    }

    // --------- helpers ---------

    public boolean existsByEmail(String email) {
        Integer cnt = jdbc.queryForObject(
                "SELECT COUNT(1) FROM public.students WHERE email=?",
                Integer.class,
                email
        );
        return cnt != null && cnt > 0;
    }

    public Student update(Long id, Student s) {
        String sql = """
            UPDATE public.students
            SET first_name=?, last_name=?, email=?, age=?
            WHERE id=?
            RETURNING *
        """;
        return jdbc.queryForObject(sql, STUDENT, s.getFirstName(), s.getLastName(), s.getEmail(), s.getAge(), id);
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM public.students WHERE id = ?", id);
    }
}