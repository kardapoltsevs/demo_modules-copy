package com.example.user.repository;

import com.example.user.model.User;
import com.example.user.model.UserBody;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
