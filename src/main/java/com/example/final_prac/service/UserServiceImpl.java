package com.example.final_prac.service;

import com.example.final_prac.model.Role;
import com.example.final_prac.model.User;
import com.example.final_prac.repository.RoleRepository;
import com.example.final_prac.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not specified
        if (user.getRole() == null) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRole(userRole);
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        if (!user.getUsername().equals(userDetails.getUsername()) &&
                existsByUsername(userDetails.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (!user.getEmail().equals(userDetails.getEmail()) &&
                existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFullName(userDetails.getFullName());
        user.setPhone(userDetails.getPhone());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
