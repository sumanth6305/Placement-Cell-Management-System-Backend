package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    List<Permission> findByPermissionName(String permissionName);

    List<Permission> findByDescriptionContaining(String keyword);

    // Add custom methods as needed

    default List<Permission> findByPermissionNameOrDescription(@Param("permissionName") String permissionName, @Param("description") String description) {
        // Custom logic to find permissions by permissionName or description
        List<Permission> permissionList = null;
        // Implement your custom logic here
        return permissionList;
    }
}
