package com.example.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final MongoService mongoService;

    // Подтягиваем email отправителя из настроек, чтобы не хардкодить
    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender, MongoService mongoService) {
        this.mailSender = mailSender;
        this.mongoService = mongoService;
    }

    /**
     * Основной метод для отправки кода подтверждения (удобный обертка)
     */
    public void sendVerificationCode(String to, String code) {
        String subject = "Код подтверждения регистрации";
        String body = "Ваш код подтверждения: " + code + "\nВведите его на странице регистрации.";

        // Вызываем асинхронный метод отправки
        sendEmailAsync(to, subject, body);
    }

    /**
     * Универсальный асинхронный метод отправки письма
     */
    @Async
    public void sendEmailAsync(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            log.info("Email успешно отправлен на адрес: {}", to);

            // Логируем успех в MongoDB через твой MongoService
            mongoService.logEmail(to, subject, "SUCCESS");

        } catch (Exception e) {
            log.error("Ошибка при отправке письма на {}. Причина: {}", to, e.getMessage());

            // Сохраняем ошибку в MongoDB
            mongoService.logEmail(to, subject, "FAILED: " + e.getMessage());
        }
    }
}