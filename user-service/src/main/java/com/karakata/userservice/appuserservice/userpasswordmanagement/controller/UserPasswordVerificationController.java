package com.karakata.userservice.appuserservice.userpasswordmanagement.controller;

import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.appuser.service.UserService;
import com.karakata.userservice.appuserservice.events.event.PasswordResetEvent;
import com.karakata.userservice.appuserservice.userpasswordmanagement.dto.PasswordToken;

import com.karakata.userservice.appuserservice.userpasswordmanagement.exception.UserPasswordVerificationNotFoundException;
import com.karakata.userservice.appuserservice.userpasswordmanagement.service.UserPasswordVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/karakata/password")
public record UserPasswordVerificationController(UserPasswordVerificationService userPasswordVerificationService,
                                                 ApplicationEventPublisher publisher, UserService userService) {

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordToken passwordToken, HttpServletRequest request) {

        User user = userService.fetchUserByUsernameOrEmailOrMobile(passwordToken.getUsernameOrMobileOrEmail());
        if (user != null) {
            publisher.publishEvent(new PasswordResetEvent(applicationUrl(request), user));
        }
        return "An email has been sent to your email to reset your password";
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestBody PasswordToken passwordTokenModel, @RequestParam("token") String token) {
        Optional<User> appUser = userPasswordVerificationService.findUserByPasswordToken(token);

        if (appUser.isPresent()) {
            userPasswordVerificationService.changeUserPassword(appUser.get(), passwordTokenModel.getNewPassword());
        }
        return "Password Reset Successfully";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordToken passwordToken) {

        User user = userService.fetchUserByUsernameOrEmailOrMobile(passwordToken.getUsernameOrMobileOrEmail());
        if (!userPasswordVerificationService.checkIfOldPasswordExist(user, passwordToken.getOldPassword())) {
            throw new UserPasswordVerificationNotFoundException("Invalid old password");
        }
        userPasswordVerificationService.changeUserPassword(user, passwordToken.getNewPassword());
        return "Password changed successfully";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
