package com.example.user.service;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Value("${spring.confl.url}")
  private String conflUrl;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public ResponseEntity<?> createUser(@Validated @RequestBody User newUser) {
    ResponseEntity<Map> response = RestClient.create(conflUrl)
        .get()
        .uri("/api/email/check?email={email}", newUser.getEmail())
        .retrieve()
        .toEntity(Map.class);

    if (Boolean.TRUE.equals(response.getBody().get("available"))) {
      return ResponseEntity.ok(userRepository.save(newUser));
    } else {
      return ResponseEntity.unprocessableEntity()
          .body("Такой email уже зарегистрирован");
    }
  }
}
