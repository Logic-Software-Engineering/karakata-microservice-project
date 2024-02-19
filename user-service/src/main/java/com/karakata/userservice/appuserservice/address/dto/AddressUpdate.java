package com.karakata.userservice.appuserservice.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdate {
    private Long address_id;
    private String fullAddress;
    private String landmark;
    private String city;
    private String state;
    private String country;
}
