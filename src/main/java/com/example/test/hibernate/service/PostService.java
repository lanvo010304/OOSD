package com.example.test.hibernate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.hibernate.model.Post;
import com.example.test.hibernate.model.Post.PostStatus;
import com.example.test.hibernate.model.User;
import com.example.test.hibernate.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }
    
    public Post createPost(String title, String body, PostStatus status, User user) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setStatus(status);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }
    
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
} 