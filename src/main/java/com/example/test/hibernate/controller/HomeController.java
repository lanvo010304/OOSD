package com.example.test.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.hibernate.model.User;
import com.example.test.hibernate.service.SessionService;
import com.example.test.hibernate.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private SessionService sessionService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("isLoggedIn", sessionService.isLoggedIn());
        if (sessionService.isLoggedIn()) {
            model.addAttribute("username", sessionService.getCurrentUser().getUsername());
        }
        return "home";
    }
    
    @GetMapping("/login")
    public String loginForm() {
        if (sessionService.isLoggedIn()) {
            return "redirect:/";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            sessionService.setCurrentUser(user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "login";
        }
    }
    
    @GetMapping("/register")
    public String registerForm() {
        if (sessionService.isLoggedIn()) {
            return "redirect:/";
        }
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.getUserByUsername(username) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "register";
        }
        
        User user = userService.registerUser(username, password);
        sessionService.setCurrentUser(user);
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout() {
        sessionService.logout();
        return "redirect:/";
    }
} 