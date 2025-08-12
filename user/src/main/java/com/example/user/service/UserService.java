package com.example.user.service;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RestClient restClient;
  private final TokenService tokenService;

  @Value("${spring.confl.url}")
  private String conflUrl;

  @Autowired
  public UserService(UserRepository userRepository,
                     TokenService tokenService,
                     RestClient.Builder restClientBuilder) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
    this.restClient = restClientBuilder.build();
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public ResponseEntity<?> createUser(@Validated @RequestBody User newUser) {
    // 1. Получаем текущий JWT токен
    String authToken = tokenService.getCurrentAuthToken();

    // 2. Проверяем email через confl-service с передачей токена
    ResponseEntity<Map<String, Boolean>> response = restClient.get()
            .uri(conflUrl + "/api/email/check?email={email}", newUser.getEmail())
            .header("Authorization", "Bearer " + authToken) // Передаем токен
            .retrieve()
            .toEntity(new ParameterizedTypeReference<Map<String, Boolean>>() {});

    // 3. Обрабатываем ответ
    if (response.getStatusCode().is2xxSuccessful() &&
            Boolean.TRUE.equals(response.getBody().get("available"))) {
      return ResponseEntity.ok(userRepository.save(newUser));
    } else {
      return ResponseEntity.unprocessableEntity()
              .body("Email уже зарегистрирован или сервис недоступен");
    }
  }
}
