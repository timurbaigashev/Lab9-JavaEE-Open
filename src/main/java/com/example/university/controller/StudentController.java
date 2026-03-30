package com.example.university.controller;

import com.example.university.dto.PageResponse;
import com.example.university.dto.StudentCreateRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.model.Student;
import com.example.university.service.StudentService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentCreateRequest req) {
        try {
            StudentResponse created = service.create(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> assign(@PathVariable Long studentId, @PathVariable Long courseId) {
        try {
            service.assignCourse(studentId, courseId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            HttpStatus st = msg != null && msg.contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(st).body(Map.of("error", msg));
        } catch (Exception e) {
            // например дубль (PK students_courses)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Student already enrolled to this course"));
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

    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> getAllPaged(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String dir
    ) {
        return ResponseEntity.ok(service.getAllPaged(page, size, sortBy, dir));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
