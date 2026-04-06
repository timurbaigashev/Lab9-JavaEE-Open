package com.example.university.service;

import com.example.university.dto.PageResponse;
import com.example.university.dto.StudentResponse;
import com.example.university.model.Student;
import com.example.university.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    void shouldReturnStudents() {
        PageResponse<StudentResponse> result = (PageResponse<StudentResponse>) service.getAllPaged();

        assertNotNull(result);
    }
}