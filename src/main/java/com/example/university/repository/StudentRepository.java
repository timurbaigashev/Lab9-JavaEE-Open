package com.example.university.repository;

import com.example.university.model.Student;
import static com.example.university.mapper.StudentRowMappers.STUDENT;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Student> mapper = (rs, rn) ->
            new Student(rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"));

    public Long insert(Student s) {
        String sql = """
            INSERT INTO public.students(first_name, last_name, email)
            VALUES (?, ?, ?)
            RETURNING id
        """;
        return jdbc.queryForObject(sql, Long.class, s.getFirstName(), s.getLastName(), s.getEmail());
    }

    public Optional<Student> findById(Long id) {
        List<Student> list = jdbc.query("SELECT * FROM students WHERE id=?", STUDENT, id);
        return list.stream().findFirst();
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM public.students WHERE id = ?", id);
    }
}
