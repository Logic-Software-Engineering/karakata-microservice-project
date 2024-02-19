package com.karakata.userservice.appuserservice.userverification.service;




import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.userverification.model.UserVerification;

import java.util.Optional;

public interface UserVerificationService {
    Optional<User> findAppUserByToken(String token);
    UserVerification generateNewVerificationToken(String token);
    void saveVerificationTokenToUser(User user, String token);
    String validateVerificationToken(String token);
}
