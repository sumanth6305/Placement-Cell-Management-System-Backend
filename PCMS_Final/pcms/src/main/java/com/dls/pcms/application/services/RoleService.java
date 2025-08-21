package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    Role createRole(Role role);

    Optional<Role> getRoleById(UUID roleId);

    List<Role> getAllRoles();

    Role updateRole(UUID roleId, Role roleDetails);

    void deleteRole(UUID roleId);

    // Add more custom methods as needed
}
