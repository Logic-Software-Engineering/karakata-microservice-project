package com.karakata.userservice.appuserservice.userpasswordmanagement.repository;



import com.karakata.userservice.appuserservice.userpasswordmanagement.model.UserPasswordVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordVerificationRepository extends JpaRepository<UserPasswordVerification, Long>, UserPasswordVerificationCustom {
}
