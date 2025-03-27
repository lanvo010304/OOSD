package com.example.test.hibernate.model;

import java.io.Serializable;
import java.util.Objects;

public class FollowId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer followingUser;
    private Integer followedUser;
    
    public FollowId() {
    }
    
    public FollowId(Integer followingUser, Integer followedUser) {
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(followingUser, followId.followingUser) && 
               Objects.equals(followedUser, followId.followedUser);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(followingUser, followedUser);
    }

    // Getters and Setters
    public Integer getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(Integer followingUser) {
        this.followingUser = followingUser;
    }

    public Integer getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(Integer followedUser) {
        this.followedUser = followedUser;
    }
} 