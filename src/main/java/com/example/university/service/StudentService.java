package com.example.university.service;

import com.example.university.common.exception.StudentNotFoundException;
import com.example.university.dto.PageResponse;
import com.example.university.dto.StudentCreateRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.mapper.StudentRowMappers;
import com.example.university.model.Student;
import com.example.university.model.StudentProfile;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.ProfileRepository;
import com.example.university.repository.StudentRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository students;
    private final ProfileRepository profiles;
    private final CourseRepository courses;
    // 1. Добавляем поле для почтового сервиса
    private final EmailService emailService;

    // 2. Обновляем конструктор (Spring автоматически внедрит зависимость)
    public StudentService(StudentRepository students,
                          ProfileRepository profiles,
                          CourseRepository courses,
                          EmailService emailService) {
        this.students = students;
        this.profiles = profiles;
        this.courses = courses;
        this.emailService = emailService;
    }

    // Task: Save Student with Profile atomically
    @Transactional
    public StudentResponse create(StudentCreateRequest req) {

        if (req.email == null || !req.email.contains("@")) {
            throw new IllegalArgumentException("Некорректный формат Email");
        }

        if (req.getFirstName() == null || req.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }

        Student s = new Student((String) null, req.getFirstName(), req.lastName, req.email, req.age );

        Long id;
        try {
            id = students.insert(s).getId();
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

        // 3. Вызов асинхронной отправки почты
        // Логика логирования в MongoDB инкапсулирована внутри emailService.sendEmailAsync
        String welcomeMsg = "Добро пожаловать, " + s.getFirstName() + "! Вы успешно зарегистрированы.";
        emailService.sendEmailAsync(s.getEmail(), "Регистрация в University", welcomeMsg);

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
        Student s = students.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

        StudentResponse r = StudentRowMappers.toResponse(s);

        // ProfileRepository возвращает уже StudentProfileDto
        r.profile = profiles.findByStudentId(id).orElse(null);

        // CourseRepository: аналогично (если возвращает уже CourseDto) — см. ниже
        r.courses = courses.findCoursesOfStudent(id); // либо .orElse(List.of()) если Optional

        return r;
    }

    public PageResponse<Student> getPage(Integer page, Integer size, String sortBy, String dir) {
        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0 || size > 100) ? 10 : size; // cap 100

        long total = students.countAll();
        List<Student> content = students.findPage(p, s, sortBy, dir);

        return new PageResponse<>(content, p, s, total);
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentResponse> getAllPaged(Integer page, Integer size, String sortBy, String dir) {
        int p = (page == null || page < 0) ? 0 : page;
        int s = (size == null || size <= 0 || size > 100) ? 10 : size;

        long total = students.countAll();

        var list = students.findPage(p, s, sortBy, dir).stream()
                .map(st -> {
                    StudentResponse r = StudentRowMappers.toResponse(st);

                    Long id = r.id;

                    r.profile = profiles.findByStudentId(id).orElse(null);
                    r.courses = courses.findCoursesOfStudent(id);

                    return r;
                })
                .toList();

        return new PageResponse<>(list, p, s, total);
    }

    // Delete Student; profile removed by FK ON DELETE CASCADE; join table also cascades
    @Transactional
    public void delete(Long id) {
        students.deleteById(id);
    }

    public Object getAllPaged() {
        return getAllPaged(0, 10, "sortBy", "dir");
    }
}