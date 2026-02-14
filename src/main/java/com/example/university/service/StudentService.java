package com.example.university.service;

import com.example.university.dto.StudentCreateRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.model.Student;
import com.example.university.model.StudentProfile;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.ProfileRepository;
import com.example.university.repository.StudentRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository students;
    private final ProfileRepository profiles;
    private final CourseRepository courses;

    public StudentService(StudentRepository students, ProfileRepository profiles, CourseRepository courses) {
        this.students = students;
        this.profiles = profiles;
        this.courses = courses;
    }

    // Task: Save Student with Profile atomically
    @Transactional
    public StudentResponse create(StudentCreateRequest req) {
        Student s = new Student(null, req.firstName, req.lastName, req.email);

        Long id;
        try {
            id = students.insert(s);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Email must be unique");
        }

        if (req.profile != null) {
            StudentProfile p = new StudentProfile();
            p.setStudentId(id);
            p.setAddress(req.profile.address);
            p.setPhone(req.profile.phone);
            p.setBirthDate(req.profile.birthDate);
            profiles.insert(p);
        }

        return getById(id);
    }

    @Transactional
    public void assignCourse(Long studentId, Long courseId) {
        // проверим существование, чтобы отдавать нормальные ошибки
        students.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        courses.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        courses.addStudentToCourse(studentId, courseId);
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        Student s = students.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found"));

        StudentResponse resp = new StudentResponse();
        resp.id = s.getId();
        resp.firstName = s.getFirstName();
        resp.lastName = s.getLastName();
        resp.email = s.getEmail();
        resp.profile = profiles.findByStudentId(id).orElse(null);
        resp.courses = courses.findCoursesOfStudent(id);
        return resp;
    }

    // Delete Student; profile removed by FK ON DELETE CASCADE; join table also cascades
    @Transactional
    public void delete(Long id) {
        students.deleteById(id);
    }
}
