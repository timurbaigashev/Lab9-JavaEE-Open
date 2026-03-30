// com.example.university.model.UserSession
package com.example.university.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "sessions")
public class Session {
    @Id
    private String id; // ID сессии в Mongo

    @Indexed
    private Long studentId; // Ссылка на ID студента из SQL

    private String token; // Сам JWT или Refresh Token


    // Поле для автоматического удаления документа через X секунд (TTL индекс)
    private Instant expiryDate;


    public Session() {
    }


    public Session(String id, Long studentId, String token, Instant expiryDate) {
        this.id = id;
        this.studentId = studentId;
        this.token = token;
        this.expiryDate = expiryDate;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Instant getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Instant expiryDate) { this.expiryDate = expiryDate; }
}