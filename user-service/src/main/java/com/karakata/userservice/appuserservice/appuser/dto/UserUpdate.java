package com.karakata.userservice.appuserservice.appuser.dto;


import com.karakata.userservice.appuserservice.address.model.Address;
import com.karakata.userservice.appuserservice.staticdata.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {
    private Long id;
    private String email;
    private String mobile;
    private UserType userType;
    private Address address;
}
