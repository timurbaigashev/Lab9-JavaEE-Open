package com.example.university.repository;

import com.example.university.model.Lesson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonRepository {
    private final JdbcTemplate jdbc;

    public LessonRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Lesson> mapper = (rs, rn) ->
            new Lesson(rs.getLong("id"), rs.getLong("course_id"),
                    rs.getString("topic"), rs.getInt("duration"));

    public void insert(Long courseId, Lesson l) {
        String sql = "INSERT INTO public.lessons(course_id, topic, duration) VALUES (?, ?, ?)";
        jdbc.update(sql, courseId, l.getTopic(), l.getDuration());
    }

    public List<Lesson> findByCourseId(Long courseId) {
        return jdbc.query("SELECT * FROM public.lessons WHERE course_id = ? ORDER BY id", mapper, courseId);
    }
}
