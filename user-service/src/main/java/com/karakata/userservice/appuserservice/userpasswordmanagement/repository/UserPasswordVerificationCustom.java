package com.karakata.userservice.appuserservice.userpasswordmanagement.repository;


import com.karakata.userservice.appuserservice.userpasswordmanagement.model.UserPasswordVerification;
import org.springframework.data.jpa.repository.Query;

public interface UserPasswordVerificationCustom {
    @Query("FROM UserPasswordVerification p WHERE p.token=?1")
    UserPasswordVerification findByToken(String token);
}
