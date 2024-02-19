package com.karakata.userservice.appuserservice.userpasswordmanagement.service;


import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.repository.UserRepository;
import com.karakata.userservice.appuserservice.userpasswordmanagement.exception.UserPasswordVerificationNotFoundException;
import com.karakata.userservice.appuserservice.userpasswordmanagement.model.UserPasswordVerification;
import com.karakata.userservice.appuserservice.userpasswordmanagement.repository.UserPasswordVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class UserPasswordVerificationServiceImpl implements UserPasswordVerificationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordVerificationRepository userPasswordVerificationRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserPasswordVerification findByToken(String token) {
        UserPasswordVerification userPasswordVerification=userPasswordVerificationRepository.findByToken(token);
        if (userPasswordVerification==null){
            throw new UserPasswordVerificationNotFoundException("Password token Not Found");
        }
        return userPasswordVerification;
    }

    @Override
    public Optional<User> findUserByPasswordToken(String token) {
        return Optional.ofNullable(userPasswordVerificationRepository.findByToken(token).getUser());
    }

    @Override
    public UserPasswordVerification createPasswordRestTokenForUser(String token, User user) {
        UserPasswordVerification passwordToken=new UserPasswordVerification(token, user);
        return userPasswordVerificationRepository.save(passwordToken);
    }

    @Override
    public String validatePasswordToken(String token) {
        UserPasswordVerification passwordToken=userPasswordVerificationRepository.findByToken(token);
        if (passwordToken==null){
            throw new UserPasswordVerificationNotFoundException("Invalid token");
        }
        Calendar calendar=Calendar.getInstance();
        if ((passwordToken.getExpectedExpirationTime().getTime()-calendar.getTime().getTime())<=0){
            userPasswordVerificationRepository.delete(passwordToken);
            throw new UserPasswordVerificationNotFoundException("Password token expired");
        }else return "Valid token";
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
//        user.setPassword(passwordEncoder.encode(newPassword)); //to work on this
        userRepository.save(user);
    }

    @Override
    public Boolean checkIfOldPasswordExist(User User, String oldPassword) {
//        return passwordEncoder.matches(user.getPassword(), oldPassword);
        return false;//To work on this
    }
}
