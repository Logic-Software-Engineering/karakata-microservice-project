package com.karakata.userservice.appuserservice.events.listener;


import com.karakata.userservice.appuserservice.config.email.EmailConfiguration;
import com.karakata.userservice.appuserservice.events.event.SellerNotificationEvent;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class SellerEventListener implements ApplicationListener<SellerNotificationEvent> {
    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(SellerNotificationEvent event) {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfiguration.getHost());
        javaMailSender.setPort(emailConfiguration.getPort());
        javaMailSender.setUsername(emailConfiguration.getUsername());
        javaMailSender.setPassword(emailConfiguration.getPassword());

        Seller seller=event.getSeller();
        SimpleMailMessage simpleMailMessage=registrationVerificationEmail(event, seller);
        javaMailSender.send(simpleMailMessage);
    }

    private SimpleMailMessage registrationVerificationEmail(SellerNotificationEvent event, Seller seller){
        String to= seller.getUser().getEmail();
        String from="fakolujos@gmail.com";
        String subject="Welcome to the Karakata family!";
        String body="Dear "+seller.getSellerName()+",\n\n"+"Your registration or update is awaiting approval!\n\n" +
                " Once the approval is completed, you will receive an email to verify your account. ";

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }
}
