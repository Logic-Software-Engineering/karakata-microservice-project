package com.karakata.userservice.appuserservice.admin.dto;

import com.karakata.userservice.appuserservice.appuser.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {
    private String firstName;
    private String lastName;
    private User user;
}
