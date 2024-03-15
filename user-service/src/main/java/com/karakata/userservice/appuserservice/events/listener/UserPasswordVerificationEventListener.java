package com.karakata.userservice.appuserservice.events.listener;



import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.config.email.EmailConfiguration;
import com.karakata.userservice.appuserservice.events.event.PasswordResetEvent;
import com.karakata.userservice.appuserservice.userpasswordmanagement.service.UserPasswordVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserPasswordVerificationEventListener implements ApplicationListener<PasswordResetEvent> {
    @Autowired
    private UserPasswordVerificationService userPasswordVerificationService;
    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(PasswordResetEvent event) {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfiguration.getHost());
        javaMailSender.setPort(emailConfiguration.getPort());
        javaMailSender.setUsername(emailConfiguration.getUsername());
        javaMailSender.setPassword(emailConfiguration.getPassword());

        String token= String.valueOf(100000 + new Random().nextInt(900000));
        User user= event.getUser();
        userPasswordVerificationService.createPasswordRestTokenForUser(token,user);
        SimpleMailMessage simpleMailMessage=passwordVerificationEmail(event,user,token);
        javaMailSender.send(simpleMailMessage);
    }

    private SimpleMailMessage passwordVerificationEmail(PasswordResetEvent event, User user, String token) {
        String to= user.getEmail();
        String from="fakolujos@gmail.com";
        String subject="Password Reset";
        String link= event.getApplicationUrl()+"/api/karakata/savePassword?token="+token;
        String text="Dear "+user.getUsername()+",\n\n"+"Click on the link in your inbox to reset your password "+link;

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        return simpleMailMessage;
    }
}
