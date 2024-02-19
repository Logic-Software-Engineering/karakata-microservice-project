package com.karakata.userservice.appuserservice.roles.repository;


import com.karakata.userservice.appuserservice.roles.model.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepositoryCustom {
    @Query("From Role r Where r.roleName=?1")
    Optional<Role> findByRoleName(String roleName);

    @Query("From Role r Where r.roleName=?1")
    Boolean existsByRoleName(String roleName);
}
