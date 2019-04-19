package com.example.springframework.oshop.services.interfaces;

import com.example.springframework.oshop.domain.User;

public interface UserService {
    User saveUser(User user);
    User findByEmail(String email);
}
