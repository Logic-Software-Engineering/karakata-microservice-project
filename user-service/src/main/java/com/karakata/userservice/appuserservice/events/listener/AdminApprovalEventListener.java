package com.karakata.userservice.appuserservice.events.listener;

import com.karakata.userservice.appuserservice.admin.model.Admin;
import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.config.email.EmailConfiguration;
import com.karakata.userservice.appuserservice.events.event.RegistrationEvent;
import com.karakata.userservice.appuserservice.events.event.AdminApprovalEvent;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class AdminApprovalEventListener implements ApplicationListener<AdminApprovalEvent> {
    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(AdminApprovalEvent event) {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfiguration.getHost());
        javaMailSender.setPort(emailConfiguration.getPort());
        javaMailSender.setUsername(emailConfiguration.getUsername());
        javaMailSender.setPassword(emailConfiguration.getPassword());

        Admin admin=event.getAdmin();
        SimpleMailMessage simpleMailMessage=registrationVerificationEmail(event, admin);
        javaMailSender.send(simpleMailMessage);
    }

    private SimpleMailMessage registrationVerificationEmail(AdminApprovalEvent event, Admin admin){
        String to= admin.getUser().getEmail();
        String from="fakolujos@gmail.com";
        String subject="Welcome to the Karakata family!";
        String link=event.getApplicationUrl()+"/api/karakata/seller/approveSellerRegistration";
        String body="Dear "+admin.getFirstName()+",\n\n"+"Seller registration or update awaiting your approval!\n\n" +
                " To approve, please click the following link "+link;

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }
}
