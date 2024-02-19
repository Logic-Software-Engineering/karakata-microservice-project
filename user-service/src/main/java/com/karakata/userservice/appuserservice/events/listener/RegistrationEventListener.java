package com.karakata.userservice.appuserservice.events.listener;



import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.config.email.EmailConfiguration;
import com.karakata.userservice.appuserservice.events.event.RegistrationEvent;
import com.karakata.userservice.appuserservice.userverification.service.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {
    @Autowired
    private UserVerificationService userVerificationService;
    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfiguration.getHost());
        javaMailSender.setPort(emailConfiguration.getPort());
        javaMailSender.setUsername(emailConfiguration.getUsername());
        javaMailSender.setPassword(emailConfiguration.getPassword());

        String token= String.valueOf(100000 + new Random().nextInt(900000));
        User user=event.getUser();
        userVerificationService.saveVerificationTokenToUser(user,token);
        SimpleMailMessage simpleMailMessage=registrationVerificationEmail(event, user,token);
        javaMailSender.send(simpleMailMessage);
    }

    private SimpleMailMessage registrationVerificationEmail(RegistrationEvent event, User user, String token){
        String to= user.getEmail();
        String from="fakolujos@gmail.com";
        String subject="Welcome to the Karakata family!";
        String link=event.getApplicationUrl()+"/api/karakata/userVerification/verifyRegistration?token="+token;
        String body="Dear "+user.getUsername()+",\n\n"+"Thanks for registering an account with Karakata!\n\n" +
                "Before you get started, you need to confirm your subscription." +
                " To confirm your subscription, please click the following link "+link;

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }
}
