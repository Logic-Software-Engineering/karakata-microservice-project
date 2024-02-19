package com.karakata.userservice.appuserservice.userverification.repository;


import com.karakata.userservice.appuserservice.userverification.model.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long>, UserVerificationCustom {
}
