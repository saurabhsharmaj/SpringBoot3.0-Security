package com.example.security.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.security.entity.User;

public interface UserService {
    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);
    
    UserDetails loadUserByUsername(@Param("username") String username);
}