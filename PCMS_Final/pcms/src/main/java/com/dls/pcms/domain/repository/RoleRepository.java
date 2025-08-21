package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByRoleName(String roleName);

    List<Role> findByDescriptionContaining(String keyword);

    // Add custom methods as needed

    default List<Role> findByRoleNameOrDescription(@Param("roleName") String roleName, @Param("description") String description) {
        // Custom logic to find roles by roleName or description
        List<Role> roleList = null;
        // Implement your custom logic here
        return roleList;
    }
}
