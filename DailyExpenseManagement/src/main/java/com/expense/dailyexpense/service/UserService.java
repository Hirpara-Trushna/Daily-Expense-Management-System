package com.expense.dailyexpense.service;

import com.expense.dailyexpense.entity.User;
import com.expense.dailyexpense.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public boolean registerUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    // Validate login
    public User validateLogin(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword() != null
                && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    // Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}