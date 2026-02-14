package com.example.university.repository;

import com.example.university.model.StudentProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbc;

    public ProfileRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<StudentProfile> mapper = (rs, rn) -> {
        StudentProfile p = new StudentProfile();
        p.setId(rs.getLong("id"));
        p.setStudentId(rs.getLong("student_id"));
        p.setAddress(rs.getString("address"));
        p.setPhone(rs.getString("phone"));
        Date bd = rs.getDate("birth_date");
        p.setBirthDate(bd == null ? null : bd.toLocalDate());
        return p;
    };

    public void insert(StudentProfile p) {
        String sql = """
            INSERT INTO public.student_profiles(student_id, address, phone, birth_date)
            VALUES (?, ?, ?, ?)
        """;
        jdbc.update(sql, p.getStudentId(), p.getAddress(), p.getPhone(),
                p.getBirthDate() == null ? null : Date.valueOf(p.getBirthDate()));
    }

    public Optional<StudentProfile> findByStudentId(Long studentId) {
        List<StudentProfile> list = jdbc.query(
                "SELECT * FROM public.student_profiles WHERE student_id = ?",
                mapper, studentId);
        return list.stream().findFirst();
    }
}
