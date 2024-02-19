package com.karakata.userservice.appuserservice.buyer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerUpdate {
    private Long id;
    private String firstName;
    private String lastName;
}
