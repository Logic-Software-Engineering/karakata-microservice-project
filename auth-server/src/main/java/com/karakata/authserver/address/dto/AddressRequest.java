package com.karakata.authserver.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String fullAddress;
    private String landmark;
    private String city;
    private String state;
    private String country;
}
