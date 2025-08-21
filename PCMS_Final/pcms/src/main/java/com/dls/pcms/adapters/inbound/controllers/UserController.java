package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.User;
import com.dls.pcms.application.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        log.info("Received request to create user with username: {}", user.getUsername());

        User createdUser = userService.createUser(user);

        if (createdUser == null) {
            log.error("User creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("User created successfully with ID: {}", createdUser.getUserId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        log.info("Received request to get user by ID: {}", userId);

        Optional<User> user = userService.getUserById(userId);

        if (user.isEmpty()) {
            log.info("No user found with ID: {}", userId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning user with ID: {}", userId);
        return ResponseEntity.ok(user.get());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Received request to get all users");

        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            log.info("No users found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID userId,
            @Validated @RequestBody User userDetails) {
        log.info("Received request to update user with ID: {}", userId);

        User updatedUser = userService.updateUser(userId, userDetails);

        if (updatedUser == null) {
            log.info("User update failed. No user found with ID: {}", userId);
            return ResponseEntity.notFound().build();
        }

        log.info("User updated successfully with ID: {}", userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        log.info("Received request to delete user with ID: {}", userId);

        userService.deleteUser(userId);

        log.info("User deleted successfully with ID: {}", userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<User>> getUsersByUsername(@PathVariable String username) {
        log.info("Received request to get users by username: {}", username);

        List<User> users = userService.getUsersByUsername(username);

        if (users.isEmpty()) {
            log.info("No users found with username: {}", username);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} users with username: {}", users.size(), username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<User>> getUsersByEmail(@PathVariable String email) {
        log.info("Received request to get users by email: {}", email);

        List<User> users = userService.getUsersByEmail(email);

        if (users.isEmpty()) {
            log.info("No users found with email: {}", email);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} users with email: {}", users.size(), email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRoleName(@PathVariable String roleName) {
        log.info("Received request to get users by role name: {}", roleName);

        List<User> users = userService.getUsersByRoleName(roleName);

        if (users.isEmpty()) {
            log.info("No users found with role name: {}", roleName);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} users with role name: {}", users.size(), roleName);
        return ResponseEntity.ok(users);
    }
}
