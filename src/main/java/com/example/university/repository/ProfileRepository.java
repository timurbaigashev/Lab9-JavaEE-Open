package com.example.university.repository;

import com.example.university.model.StudentProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.university.mapper.StudentRowMappers.PROFILE;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbc;

    public ProfileRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void insert(StudentProfile p) {
        jdbc.update("""
            INSERT INTO public.student_profiles(student_id, address, phone, birth_date)
            VALUES (?, ?, ?, ?)
        """, p.getStudentId(), p.getAddress(), p.getPhone(), p.getBirthDate());
    }

    public Optional<StudentProfile> findByStudentId(Long studentId) {
        List<StudentProfile> list = jdbc.query(
                "SELECT * FROM public.student_profiles WHERE student_id = ?",
                PROFILE,
                studentId
        );
        return list.stream().findFirst();
    }
}