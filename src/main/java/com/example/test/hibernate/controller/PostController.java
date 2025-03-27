package com.example.test.hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.hibernate.model.Post;
import com.example.test.hibernate.model.Post.PostStatus;
import com.example.test.hibernate.model.User;
import com.example.test.hibernate.service.PostService;
import com.example.test.hibernate.service.SessionService;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private SessionService sessionService;
    
    @GetMapping
    public String listPosts(Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        List<Post> posts = postService.getPostsByUser(currentUser);
        model.addAttribute("posts", posts);
        model.addAttribute("username", currentUser.getUsername());
        return "posts/list";
    }
    
    @GetMapping("/new")
    public String newPostForm(Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        return "posts/new";
    }
    
    @PostMapping("/new")
    public String createPost(
            @RequestParam String title, 
            @RequestParam String body, 
            @RequestParam String status) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        User currentUser = sessionService.getCurrentUser();
        PostStatus postStatus = PostStatus.valueOf(status);
        postService.createPost(title, body, postStatus, currentUser);
        
        return "redirect:/posts";
    }
    
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Integer id, Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }
        
        Post post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/posts";
        }
        
        model.addAttribute("post", post);
        return "posts/view";
    }
} 