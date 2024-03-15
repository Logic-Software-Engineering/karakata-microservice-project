package com.karakata.authserver.roles.repository;


import com.karakata.authserver.roles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>, RoleRepositoryCustom {
}
