package com.karakata.authserver.appuser.repository;



import com.karakata.authserver.appuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
