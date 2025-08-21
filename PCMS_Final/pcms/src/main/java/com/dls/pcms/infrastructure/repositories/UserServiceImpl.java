package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.UserService;
import com.dls.pcms.domain.models.User;
import com.dls.pcms.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating new user with username: {}", user.getUsername());
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        log.info("Fetching user by ID: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.info("User found with ID: {}", userId);
        } else {
            log.info("No user found with ID: {}", userId);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("Found {} users", users.size());
        return users;
    }

    @Override
    public User updateUser(UUID userId, User userDetails) {
        log.info("Updating user with ID: {}", userId);
        Optional<User> existingUserOpt = userRepository.findById(userId);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setHashedPassword(userDetails.getHashedPassword());
            existingUser.setSalt(userDetails.getSalt());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setRole(userDetails.getRole());
            existingUser.setUpdatedAt(userDetails.getUpdatedAt());

            User updatedUser = userRepository.save(existingUser);
            log.info("User updated successfully with ID: {}", userId);
            return updatedUser;
        } else {
            log.error("No user found with ID: {}", userId);
            return null;
        }
    }

    @Override
    public void deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);
        userRepository.deleteById(userId);
        log.info("User deleted successfully with ID: {}", userId);
    }

    @Override
    public List<User> getUsersByUsername(String username) {
        log.info("Fetching users with username: {}", username);
        List<User> users = userRepository.findByUsername(username);
        log.info("Found {} users with username: {}", users.size(), username);
        return users;
    }

    @Override
    public List<User> getUsersByEmail(String email) {
        log.info("Fetching users with email: {}", email);
        List<User> users = userRepository.findByEmail(email);
        log.info("Found {} users with email: {}", users.size(), email);
        return users;
    }

    @Override
    public List<User> getUsersByRoleName(String roleName) {
        log.info("Fetching users with role name: {}", roleName);
        List<User> users = userRepository.findByRoleRoleName(roleName);
        log.info("Found {} users with role name: {}", users.size(), roleName);
        return users;
    }
}
