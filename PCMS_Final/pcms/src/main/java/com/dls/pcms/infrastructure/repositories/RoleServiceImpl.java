package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.RoleService;
import com.dls.pcms.domain.models.Role;
import com.dls.pcms.domain.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        log.info("Creating new role with name: {}", role.getRoleName());
        Role savedRole = roleRepository.save(role);
        log.info("Role created successfully with ID: {}", savedRole.getRoleId());
        return savedRole;
    }

    @Override
    public Optional<Role> getRoleById(UUID roleId) {
        log.info("Fetching role by ID: {}", roleId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            log.info("Role found with ID: {}", roleId);
        } else {
            log.info("No role found with ID: {}", roleId);
        }
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("Fetching all roles");
        List<Role> roles = roleRepository.findAll();
        log.info("Found {} roles", roles.size());
        return roles;
    }

    @Override
    public Role updateRole(UUID roleId, Role roleDetails) {
        log.info("Updating role with ID: {}", roleId);
        Optional<Role> existingRoleOpt = roleRepository.findById(roleId);

        if (existingRoleOpt.isPresent()) {
            Role existingRole = existingRoleOpt.get();
            existingRole.setRoleName(roleDetails.getRoleName());
            existingRole.setDescription(roleDetails.getDescription());
            existingRole.setUpdatedAt(roleDetails.getUpdatedAt());

            Role updatedRole = roleRepository.save(existingRole);
            log.info("Role updated successfully with ID: {}", roleId);
            return updatedRole;
        } else {
            log.error("No role found with ID: {}", roleId);
            return null;
        }
    }

    @Override
    public void deleteRole(UUID roleId) {
        log.info("Deleting role with ID: {}", roleId);
        roleRepository.deleteById(roleId);
        log.info("Role deleted successfully with ID: {}", roleId);
    }
}
