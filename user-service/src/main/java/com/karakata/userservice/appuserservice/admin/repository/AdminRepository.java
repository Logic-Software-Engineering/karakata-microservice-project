package com.karakata.userservice.appuserservice.admin.repository;



import com.karakata.userservice.appuserservice.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long>, AdminRepositoryCustom {
}
