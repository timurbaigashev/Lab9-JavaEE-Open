package com.example.university.mapper;

import com.example.university.model.Student;
import org.springframework.jdbc.core.RowMapper;

public final class StudentRowMappers {
    private StudentRowMappers() {}

    public static final RowMapper<Student> STUDENT = (rs, rn) ->
            new Student(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email")
            );
}
