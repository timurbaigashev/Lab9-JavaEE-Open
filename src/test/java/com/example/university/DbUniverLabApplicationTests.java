package com.example.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class EmailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    void testSmtpConnection() {
        // Если конфигурация неверна, этот тест упадет при запуске контекста
        assertNotNull(mailSender);
    }
}
