package com.example.university.mapper;

import com.example.university.model.Course;
import org.springframework.jdbc.core.RowMapper;

public class CourseRowMappers {
    private CourseRowMappers() {}

    public static final RowMapper<Course> COURSE = (rs, rn) ->
            new Course(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getInt("credits")
            );
}
