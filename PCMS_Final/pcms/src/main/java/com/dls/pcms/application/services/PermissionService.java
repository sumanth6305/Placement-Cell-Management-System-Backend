package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionService {

    Permission createPermission(Permission permission);

    Optional<Permission> getPermissionById(UUID permissionId);

    List<Permission> getAllPermissions();

    Permission updatePermission(UUID permissionId, Permission permissionDetails);

    void deletePermission(UUID permissionId);

    // Add more custom methods as needed
}
