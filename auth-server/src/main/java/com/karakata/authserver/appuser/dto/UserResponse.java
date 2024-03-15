package com.karakata.authserver.appuser.dto;


import com.karakata.authserver.staticdata.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private UserType userType;
    private String username;
    private String email;
    private String mobile;
    private String fullAddress;
    private String landmark;
    private String city;
    private String state;
    private String country;
}
