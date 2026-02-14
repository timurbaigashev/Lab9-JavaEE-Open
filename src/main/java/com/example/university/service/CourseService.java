package com.example.university.service;

import com.example.university.dto.CourseCreateRequest;
import com.example.university.dto.CourseResponse;
import com.example.university.dto.LessonCreateRequest;
import com.example.university.model.Course;
import com.example.university.model.Lesson;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courses;
    private final LessonRepository lessons;

    public CourseService(CourseRepository courses, LessonRepository lessons) {
        this.courses = courses;
        this.lessons = lessons;
    }

    // atomic create course (can be extended to include lessons in one transaction)
    @Transactional
    public CourseResponse create(CourseCreateRequest req) {
        Course c = new Course(null, req.title, req.credits);
        Long id = courses.insert(c);
        return getById(id);
    }

    // Task: add lessons atomically
    @Transactional
    public CourseResponse addLessons(Long courseId, List<LessonCreateRequest> reqs) {
        courses.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        for (LessonCreateRequest r : reqs) {
            Lesson l = new Lesson(null, courseId, r.topic, r.duration);
            lessons.insert(courseId, l);
        }
        return getById(courseId);
    }

    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {
        Course c = courses.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        CourseResponse resp = new CourseResponse();
        resp.id = c.getId();
        resp.title = c.getTitle();
        resp.credits = c.getCredits();
        resp.lessons = lessons.findByCourseId(id);
        return resp;
    }
}
