package com.karakata.userservice.appuserservice.appuser.dto;


import com.karakata.userservice.appuserservice.address.model.Address;
import com.karakata.userservice.appuserservice.staticdata.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private UserType userType;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private String confirmPassword;
    private Address address;
}
