package com.karakata.userservice.appuserservice.buyer.dto;


import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.staticdata.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private User user;
}
