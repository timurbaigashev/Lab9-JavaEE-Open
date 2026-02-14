package com.example.university.controller;

import com.example.university.dto.CourseCreateRequest;
import com.example.university.dto.LessonCreateRequest;
import com.example.university.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CourseCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<?> addLessons(@PathVariable Long courseId, @RequestBody List<LessonCreateRequest> lessons) {
        try {
            return ResponseEntity.ok(service.addLessons(courseId, lessons));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
