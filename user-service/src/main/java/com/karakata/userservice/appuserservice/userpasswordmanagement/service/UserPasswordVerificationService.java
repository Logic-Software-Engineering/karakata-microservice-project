package com.karakata.userservice.appuserservice.userpasswordmanagement.service;




import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.userpasswordmanagement.model.UserPasswordVerification;

import java.util.Optional;

public interface UserPasswordVerificationService {
    UserPasswordVerification findByToken(String token);
    Optional<User> findUserByPasswordToken(String token);
    UserPasswordVerification createPasswordRestTokenForUser(String token, User appUser);
    String validatePasswordToken(String token);
    void changeUserPassword(User user, String password);
    Boolean checkIfOldPasswordExist(User user, String oldPassword);
}
