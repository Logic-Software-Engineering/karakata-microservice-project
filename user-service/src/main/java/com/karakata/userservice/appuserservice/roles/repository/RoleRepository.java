package com.karakata.userservice.appuserservice.roles.repository;


import com.karakata.userservice.appuserservice.roles.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>, RoleRepositoryCustom {
}
