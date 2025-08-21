package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByUsername(String username);

    List<User> findByEmail(String email);

    List<User> findByRoleRoleName(String roleName); // Assuming 'roleName' field in Role entity

    // Add custom methods as needed

    default List<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email) {
        // Custom logic to find users by username or email
        List<User> userList = null;
        // Implement your custom logic here
        return userList;
    }
}
