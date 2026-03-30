package com.example.university.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; // Обязательный импорт
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MongoTemplate mongoTemplate;

    public JwtFilter(JwtProvider jwtProvider, MongoTemplate mongoTemplate) {
        this.jwtProvider = jwtProvider;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Проверка в MongoDB (наша сессия)
            Query query = new Query(Criteria.where("token").is(token));
            boolean sessionExists = mongoTemplate.exists(query, "sessions");

            if (sessionExists && jwtProvider.validateToken(token)) {
                String email = jwtProvider.getEmailFromToken(token);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Если здесь ошибка "Unhandled exception", значит в заголовке метода выше
        // отсутствует "throws IOException"
        filterChain.doFilter(request, response);
    }
}