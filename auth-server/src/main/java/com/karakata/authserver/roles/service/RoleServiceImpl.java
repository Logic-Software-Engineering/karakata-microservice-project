package com.karakata.authserver.roles.service;




import com.karakata.authserver.roles.exception.RoleNotFoundException;
import com.karakata.authserver.roles.model.Role;
import com.karakata.authserver.roles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role fetchRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(()->new RoleNotFoundException("Role " + roleName + " not found"));
    }

    @Override
    public List<Role> fetchAllRoles(int pageNumber, int pageSize) {
        return roleRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }

    @Override
    public Role editRole(Role role, String roleName) {
        Role savedRole=roleRepository.findByRoleName(roleName)
                .orElseThrow(()->new RoleNotFoundException("Role " + roleName + " not found"));
        if (Objects.nonNull(role.getRoleName()) && !"".equalsIgnoreCase(role.getRoleName())){
            savedRole.setRoleName(role.getRoleName());
        }
        return roleRepository.save(savedRole);
    }

    @Override
    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)){
            roleRepository.deleteById(id);
        }else throw new RoleNotFoundException("Role " + id + " not found");
    }
}
