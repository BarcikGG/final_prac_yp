package com.example.final_prac.service;

import com.example.final_prac.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

