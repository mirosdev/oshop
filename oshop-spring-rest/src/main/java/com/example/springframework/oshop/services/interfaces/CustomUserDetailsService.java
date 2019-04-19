package com.example.springframework.oshop.services.interfaces;

import com.example.springframework.oshop.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    User loadUserById(Long id);
}
