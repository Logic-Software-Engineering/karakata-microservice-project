package com.karakata.userservice.appuserservice.userpasswordmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordToken {
    private String usernameOrMobileOrEmail;
    private String oldPassword;
    private String newPassword;
}
