package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.Role;
import com.dls.pcms.application.services.RoleService;
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
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@Validated @RequestBody Role role) {
        log.info("Received request to create role with name: {}", role.getRoleName());

        Role createdRole = roleService.createRole(role);

        if (createdRole == null) {
            log.error("Role creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Role created successfully with ID: {}", createdRole.getRoleId());
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable UUID roleId) {
        log.info("Received request to get role by ID: {}", roleId);

        Optional<Role> role = roleService.getRoleById(roleId);

        if (role.isEmpty()) {
            log.info("No role found with ID: {}", roleId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning role with ID: {}", roleId);
        return ResponseEntity.ok(role.get());
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        log.info("Received request to get all roles");

        List<Role> roles = roleService.getAllRoles();

        if (roles.isEmpty()) {
            log.info("No roles found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} roles", roles.size());
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(
            @PathVariable UUID roleId,
            @Validated @RequestBody Role roleDetails) {
        log.info("Received request to update role with ID: {}", roleId);

        Role updatedRole = roleService.updateRole(roleId, roleDetails);

        if (updatedRole == null) {
            log.info("Role update failed. No role found with ID: {}", roleId);
            return ResponseEntity.notFound().build();
        }

        log.info("Role updated successfully with ID: {}", roleId);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID roleId) {
        log.info("Received request to delete role with ID: {}", roleId);

        roleService.deleteRole(roleId);

        log.info("Role deleted successfully with ID: {}", roleId);
        return ResponseEntity.noContent().build();
    }
}
