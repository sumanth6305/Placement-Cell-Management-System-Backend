package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.PermissionService;
import com.dls.pcms.domain.models.Permission;
import com.dls.pcms.domain.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission permission) {
        log.info("Creating new permission with name: {}", permission.getPermissionName());
        Permission savedPermission = permissionRepository.save(permission);
        log.info("Permission created successfully with ID: {}", savedPermission.getPermissionId());
        return savedPermission;
    }

    @Override
    public Optional<Permission> getPermissionById(UUID permissionId) {
        log.info("Fetching permission by ID: {}", permissionId);
        Optional<Permission> permission = permissionRepository.findById(permissionId);
        if (permission.isPresent()) {
            log.info("Permission found with ID: {}", permissionId);
        } else {
            log.info("No permission found with ID: {}", permissionId);
        }
        return permission;
    }

    @Override
    public List<Permission> getAllPermissions() {
        log.info("Fetching all permissions");
        List<Permission> permissions = permissionRepository.findAll();
        log.info("Found {} permissions", permissions.size());
        return permissions;
    }

    @Override
    public Permission updatePermission(UUID permissionId, Permission permissionDetails) {
        log.info("Updating permission with ID: {}", permissionId);
        Optional<Permission> existingPermissionOpt = permissionRepository.findById(permissionId);

        if (existingPermissionOpt.isPresent()) {
            Permission existingPermission = existingPermissionOpt.get();
            existingPermission.setPermissionName(permissionDetails.getPermissionName());
            existingPermission.setDescription(permissionDetails.getDescription());
            existingPermission.setUpdatedAt(permissionDetails.getUpdatedAt());

            Permission updatedPermission = permissionRepository.save(existingPermission);
            log.info("Permission updated successfully with ID: {}", permissionId);
            return updatedPermission;
        } else {
            log.error("No permission found with ID: {}", permissionId);
            return null;
        }
    }

    @Override
    public void deletePermission(UUID permissionId) {
        log.info("Deleting permission with ID: {}", permissionId);
        permissionRepository.deleteById(permissionId);
        log.info("Permission deleted successfully with ID: {}", permissionId);
    }
}
