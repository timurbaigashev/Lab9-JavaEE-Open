package com.example.university.config;

import com.example.university.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class MongoConfig {
    @Autowired
    private MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        // Программное создание TTL индекса
        mongoTemplate.indexOps(Session.class)
                .ensureIndex(new Index().on("expiryDate", Sort.Direction.ASC).expire(0));
    }
}
