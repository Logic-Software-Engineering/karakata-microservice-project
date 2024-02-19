package com.karakata.userservice.appuserservice.appuser.repository;


import com.karakata.userservice.appuserservice.appuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
