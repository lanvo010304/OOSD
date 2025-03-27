package com.example.test.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.hibernate.model.Follow;
import com.example.test.hibernate.model.FollowId;
import com.example.test.hibernate.model.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findByFollowingUser(User followingUser);
    List<Follow> findByFollowedUser(User followedUser);
    Follow findByFollowingUserAndFollowedUser(User followingUser, User followedUser);
} 