package com.karakata.userservice.appuserservice.userverification.repository;



import com.karakata.userservice.appuserservice.userverification.model.UserVerification;
import org.springframework.data.jpa.repository.Query;

public interface UserVerificationCustom {
    @Query("From UserVerification u Where u.token=?1")
    UserVerification findByToken(String token);
}
