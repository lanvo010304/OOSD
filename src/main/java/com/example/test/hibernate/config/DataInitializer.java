package com.example.test.hibernate.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.test.hibernate.model.Follow;
import com.example.test.hibernate.model.Post;
import com.example.test.hibernate.model.Post.PostStatus;
import com.example.test.hibernate.model.User;
import com.example.test.hibernate.model.User.UserRole;
import com.example.test.hibernate.repository.FollowRepository;
import com.example.test.hibernate.repository.PostRepository;
import com.example.test.hibernate.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private FollowRepository followRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra nếu đã có dữ liệu thì không tạo nữa
        if (userRepository.count() > 0) {
            return;
        }
        
        // Tạo người dùng
        User user1 = createUser("user1", "password1", UserRole.user);
        User user2 = createUser("user2", "password2", UserRole.user);
        User admin = createUser("admin", "admin", UserRole.admin);
        
        // Tạo bài viết
        createPost("Bài viết đầu tiên của user1", "Đây là nội dung bài viết đầu tiên của user1.\nChào mừng đến với ứng dụng mạng xã hội đơn giản!", user1, PostStatus.published);
        createPost("Bài viết nháp của user1", "Đây là bài viết đang nháp của user1, chưa hoàn thiện.", user1, PostStatus.draft);
        createPost("Bài viết của user2", "Đây là bài viết của user2.\nRất vui được tham gia mạng xã hội này!", user2, PostStatus.published);
        createPost("Thông báo từ Admin", "Đây là thông báo từ Admin về việc sử dụng ứng dụng mạng xã hội đơn giản.", admin, PostStatus.published);
        
        // Tạo quan hệ follow
        createFollow(user1, user2);
        createFollow(user2, user1);
        createFollow(user1, admin);
        createFollow(user2, admin);
    }
    
    private User createUser(String username, String password, UserRole role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    private Post createPost(String title, String body, User user, PostStatus status) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);
        post.setStatus(status);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
    
    private Follow createFollow(User followingUser, User followedUser) {
        Follow follow = new Follow();
        follow.setFollowingUser(followingUser);
        follow.setFollowedUser(followedUser);
        follow.setCreatedAt(LocalDateTime.now());
        return followRepository.save(follow);
    }
} 