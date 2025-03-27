package com.example.test.hibernate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.hibernate.model.User;
import com.example.test.hibernate.model.User.UserRole;
import com.example.test.hibernate.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Trong ứng dụng thực tế, cần mã hóa password
        user.setRole(UserRole.user);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
} 