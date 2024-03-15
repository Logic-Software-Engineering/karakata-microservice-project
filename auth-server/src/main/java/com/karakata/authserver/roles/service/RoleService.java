package com.karakata.authserver.roles.service;






import com.karakata.authserver.roles.model.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role fetchRoleByName(String roleName);
    List<Role> fetchAllRoles(int pageNumber, int pageSize);
    Role editRole(Role role, String roleName);
    void deleteRole(Long id);
}
