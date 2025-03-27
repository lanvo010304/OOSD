package com.example.test.hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.test.hibernate.model.User;
import com.example.test.hibernate.service.FollowService;
import com.example.test.hibernate.service.SessionService;
import com.example.test.hibernate.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private FollowService followService;
    
    @Autowired
    private SessionService sessionService;
    
    @GetMapping
    public String listUsers(Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        
        // Kiểm tra người dùng nào đang được follow
        for (User user : users) {
            boolean isFollowing = followService.isFollowing(currentUser, user);
            user.getFollowers().add(null); // Hack để tạo map trong Thymeleaf, sẽ sử dụng size để kiểm tra
            if (isFollowing) {
                user.getFollowers().add(null);
            }
        }
        
        return "users/list";
    }
    
    @GetMapping("/following")
    public String listFollowing(Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        List<User> followingUsers = followService.getFollowingUsers(currentUser);
        model.addAttribute("users", followingUsers);
        model.addAttribute("title", "Người dùng bạn đang theo dõi");
        
        return "users/following";
    }
    
    @GetMapping("/followers")
    public String listFollowers(Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        List<User> followerUsers = followService.getFollowerUsers(currentUser);
        model.addAttribute("users", followerUsers);
        model.addAttribute("title", "Người đang theo dõi bạn");
        
        return "users/followers";
    }
    
    @PostMapping("/{id}/follow")
    public String followUser(@PathVariable Integer id) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        User userToFollow = userService.getUserById(id);
        
        if (userToFollow != null && !currentUser.getId().equals(userToFollow.getId())) {
            followService.followUser(currentUser, userToFollow);
        }
        
        return "redirect:/users";
    }
    
    @PostMapping("/{id}/unfollow")
    public String unfollowUser(@PathVariable Integer id) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        User userToUnfollow = userService.getUserById(id);
        
        if (userToUnfollow != null) {
            followService.unfollowUser(currentUser, userToUnfollow);
        }
        
        return "redirect:/users";
    }
} 