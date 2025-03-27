package com.example.test.hibernate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.test.hibernate.model.User;

@Service
@SessionScope
public class SessionService {
    
    private User currentUser;
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public void logout() {
        this.currentUser = null;
    }
} 