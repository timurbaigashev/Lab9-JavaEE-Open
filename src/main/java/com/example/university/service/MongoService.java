package com.example.university.service;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class MongoService {
    private final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private final MongoDatabase database = mongoClient.getDatabase("lab7_db");

    public void logEmail(String recipient, String subject, String status) {
        MongoCollection<Document> collection = database.getCollection("email_logs");
        Document log = new Document("recipient", recipient)
                .append("subject", subject)
                .append("status", status)
                .append("timestamp", System.currentTimeMillis());
        collection.insertOne(log);
    }
}