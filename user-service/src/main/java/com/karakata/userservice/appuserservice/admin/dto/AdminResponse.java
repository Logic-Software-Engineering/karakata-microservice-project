package com.karakata.userservice.appuserservice.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String fullAddress;
    private String landmark;
    private String city;
    private String state;
    private String country;
}
