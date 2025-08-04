package com.example.confl.controller;

import com.example.confl.model.Email;
import com.example.confl.service.ConflService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class ConflController {

  @Autowired
  private final ConflService conflService;

  @Autowired
  public ConflController(ConflService conflService) {
    this.conflService = conflService;
  }

  @GetMapping
  public List<Email> getAllEmails() {
    return conflService.getAllEmails();
  }

  @GetMapping("/check")
  public ResponseEntity<?> checkEmail(@RequestParam String email) {
    boolean available = conflService.isEmailAvailable(email);
    return ResponseEntity.ok().body(Map.of("available", available));
  }
}
