package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.Permission;
import com.dls.pcms.application.services.PermissionService;
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
@RequestMapping("/api/permissions")
@Slf4j
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<Permission> createPermission(@Validated @RequestBody Permission permission) {
        log.info("Received request to create permission with name: {}", permission.getPermissionName());

        Permission createdPermission = permissionService.createPermission(permission);

        if (createdPermission == null) {
            log.error("Permission creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Permission created successfully with ID: {}", createdPermission.getPermissionId());
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable UUID permissionId) {
        log.info("Received request to get permission by ID: {}", permissionId);

        Optional<Permission> permission = permissionService.getPermissionById(permissionId);

        if (permission.isEmpty()) {
            log.info("No permission found with ID: {}", permissionId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning permission with ID: {}", permissionId);
        return ResponseEntity.ok(permission.get());
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        log.info("Received request to get all permissions");

        List<Permission> permissions = permissionService.getAllPermissions();

        if (permissions.isEmpty()) {
            log.info("No permissions found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} permissions", permissions.size());
        return ResponseEntity.ok(permissions);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Permission> updatePermission(
            @PathVariable UUID permissionId,
            @Validated @RequestBody Permission permissionDetails) {
        log.info("Received request to update permission with ID: {}", permissionId);

        Permission updatedPermission = permissionService.updatePermission(permissionId, permissionDetails);

        if (updatedPermission == null) {
            log.info("Permission update failed. No permission found with ID: {}", permissionId);
            return ResponseEntity.notFound().build();
        }

        log.info("Permission updated successfully with ID: {}", permissionId);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable UUID permissionId) {
        log.info("Received request to delete permission with ID: {}", permissionId);

        permissionService.deletePermission(permissionId);

        log.info("Permission deleted successfully with ID: {}", permissionId);
        return ResponseEntity.noContent().build();
    }
}
