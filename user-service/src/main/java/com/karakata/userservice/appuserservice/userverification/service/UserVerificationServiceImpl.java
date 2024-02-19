package com.karakata.userservice.appuserservice.userverification.service;



import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.repository.UserRepository;
import com.karakata.userservice.appuserservice.userverification.exception.UserVerificationNotFoundException;
import com.karakata.userservice.appuserservice.userverification.model.UserVerification;
import com.karakata.userservice.appuserservice.userverification.repository.UserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserVerificationServiceImpl implements UserVerificationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Override
    public Optional<User> findAppUserByToken(String token) {
        return Optional.ofNullable(userVerificationRepository.findByToken(token).getUser());
    }

    @Override
    public UserVerification generateNewVerificationToken(String token) {
        UserVerification userVerification=userVerificationRepository.findByToken(token);
        if (userVerification==null){
            throw new UserVerificationNotFoundException("Token Not Found");
        }
        userVerification.setToken(String.valueOf(100000+new Random().nextInt(900000)));
        return userVerificationRepository.save(userVerification);
    }

    @Override
    public void saveVerificationTokenToUser(User user, String token) {
        UserVerification userVerification=new UserVerification(token,user);
        userVerificationRepository.save(userVerification);
    }

    @Override
    public String validateVerificationToken(String token) {
        UserVerification userVerification=userVerificationRepository.findByToken(token);
        if (userVerification==null){
            throw new UserVerificationNotFoundException("Invalid token");
        }
        User user=userVerification.getUser();
        Calendar calendar=Calendar.getInstance();
        if ((userVerification.getExpectedExpirationTime().getTime()-calendar.getTime().getTime())<=0){
            userVerificationRepository.delete(userVerification);
            throw new UserVerificationNotFoundException("Token expired");
        }
        user.setIsEnabled(true);
        userRepository.save(user);

        return "Valid token";
    }
}
