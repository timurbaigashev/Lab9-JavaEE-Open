package com.example.university.repository;

import com.example.university.model.Course;
import static com.example.university.mapper.CourseRowMappers.COURSE;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    private final JdbcTemplate jdbc;

    public CourseRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public Long insert(Course c) {
        String sql = "INSERT INTO public.courses(title, credits) VALUES (?, ?) RETURNING id";
        return jdbc.queryForObject(sql, Long.class, c.getTitle(), c.getCredits());
    }

    public Optional<Course> findById(Long id) {
        List<Course> list = jdbc.query("SELECT * FROM public.courses WHERE id = ?", COURSE, id);
        return list.stream().findFirst();
    }

    public List<Course> findCoursesOfStudent(Long studentId) {
        String sql = """
            SELECT c.*
            FROM public.courses c
            JOIN public.students_courses sc ON sc.course_id = c.id
            WHERE sc.student_id = ?
            ORDER BY c.id
        """;
        return jdbc.query(sql, COURSE, studentId);
    }

    public void addStudentToCourse(Long studentId, Long courseId) {
        // защита от дублей: PK(student_id, course_id)
        jdbc.update("INSERT INTO public.students_courses(student_id, course_id) VALUES (?, ?)",
                studentId, courseId);
    }
}
