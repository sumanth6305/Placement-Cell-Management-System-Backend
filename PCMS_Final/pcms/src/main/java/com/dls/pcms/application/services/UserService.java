package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(UUID userId);

    List<User> getAllUsers();

    User updateUser(UUID userId, User userDetails);

    void deleteUser(UUID userId);

    List<User> getUsersByUsername(String username);

    List<User> getUsersByEmail(String email);

    List<User> getUsersByRoleName(String roleName); // Assuming 'roleName' field in Role entity

    // Add more custom methods as needed
}
