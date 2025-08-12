package com.example.user.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;;
import org.springframework.stereotype.Service;
@Service
public class TokenService {
    public String getCurrentAuthToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() != null) {
            // Для JWT - используем Jwt из spring-security-oauth2-jose
            if (authentication.getPrincipal() instanceof Jwt) {
                return ((Jwt) authentication.getPrincipal()).getTokenValue();
            }
            // Для базовой аутентификации
            else if (authentication.getCredentials() instanceof String) {
                return (String) authentication.getCredentials();
            }
        }
        throw new IllegalStateException("Токен аутентификации не найден");
    }
}