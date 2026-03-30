package com.example.university.mapper;

import com.example.university.dto.CourseResponse;
import com.example.university.dto.StudentResponse;
import com.example.university.model.Course;
import com.example.university.model.Student;
import com.example.university.model.StudentProfile;
import org.springframework.jdbc.core.RowMapper;

public final class StudentRowMappers {
    private StudentRowMappers() {}

    public static final RowMapper<Student> STUDENT = (rs, rn) -> {
        Student s = new Student();
        s.setId(rs.getLong("id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setEmail(rs.getString("email"));
        s.setAge(rs.getInt("age"));
        // Если в таблице есть дата создания:
        // s.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        return s;
    };

    // Если у тебя другая схема profile/course — поправь названия колонок тут
    public static final RowMapper<StudentProfile> PROFILE = (rs, rn) -> {
        StudentProfile p = new StudentProfile();
        p.setId(rs.getLong("id")); // если у profile есть id
        p.setStudentId(rs.getLong("student_id"));
        p.setAddress(rs.getString("address"));
        p.setPhone(rs.getString("phone"));
        p.setBirthDate(rs.getObject("birth_date", java.time.LocalDate.class));
        return p;
    };

    // ---------- COURSE ----------
    // предполагаем таблицу courses(id, name)
    public static final RowMapper<Course> COURSE = (rs, rn) -> {
        Course c = new Course();
        c.setId(rs.getLong("id"));
        c.setTitle(rs.getString("title"));
        return c;
    };

    // ---------- DTO mappers ----------
    public static StudentResponse toResponse(Student s) {
        StudentResponse dto = new StudentResponse();
        dto.id = s.getId();
        dto.firstName = s.getFirstName();
        dto.lastName = s.getLastName();
        dto.email = s.getEmail();
        dto.age = s.getAge();
        dto.createdAt = s.getCreatedAt();
        return dto;
    }

    public static StudentResponse.StudentProfileDto toProfileResponse(StudentProfile p) {
        if (p == null) return null;
        StudentResponse.StudentProfileDto dto = new StudentResponse.StudentProfileDto();
        dto.id = p.getId();
        dto.address = p.getAddress();
        dto.phone = p.getPhone();
        dto.birthDate = p.getBirthDate();
        return dto;
    }

    public static CourseResponse toCourseResponse(Course c) {
        if (c == null) return null;
        CourseResponse dto = new CourseResponse();
        dto.id = c.getId();
        dto.title = c.getTitle();
        return dto;
    }
}
