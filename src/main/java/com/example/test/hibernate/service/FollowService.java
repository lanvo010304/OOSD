package com.example.test.hibernate.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.hibernate.model.Follow;
import com.example.test.hibernate.model.User;
import com.example.test.hibernate.repository.FollowRepository;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;
    
    public List<Follow> getFollowingByUser(User user) {
        return followRepository.findByFollowingUser(user);
    }
    
    public List<Follow> getFollowersByUser(User user) {
        return followRepository.findByFollowedUser(user);
    }
    
    public List<User> getFollowingUsers(User user) {
        List<Follow> follows = followRepository.findByFollowingUser(user);
        List<User> followingUsers = new ArrayList<>();
        for (Follow follow : follows) {
            followingUsers.add(follow.getFollowedUser());
        }
        return followingUsers;
    }
    
    public List<User> getFollowerUsers(User user) {
        List<Follow> follows = followRepository.findByFollowedUser(user);
        List<User> followerUsers = new ArrayList<>();
        for (Follow follow : follows) {
            followerUsers.add(follow.getFollowingUser());
        }
        return followerUsers;
    }
    
    public boolean isFollowing(User followingUser, User followedUser) {
        return followRepository.findByFollowingUserAndFollowedUser(followingUser, followedUser) != null;
    }
    
    public Follow followUser(User followingUser, User followedUser) {
        // Kiểm tra follow chính mình
        if (followingUser.getId().equals(followedUser.getId())) {
            return null;
        }
        
        // Kiểm tra follow trùng lặp
        Follow existingFollow = followRepository.findByFollowingUserAndFollowedUser(followingUser, followedUser);
        if (existingFollow != null) {
            return existingFollow;
        }
        
        Follow follow = new Follow();
        follow.setFollowingUser(followingUser);
        follow.setFollowedUser(followedUser);
        follow.setCreatedAt(LocalDateTime.now());
        return followRepository.save(follow);
    }
    
    public void unfollowUser(User followingUser, User followedUser) {
        Follow follow = followRepository.findByFollowingUserAndFollowedUser(followingUser, followedUser);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }
} 