package com.karakata.userservice.appuserservice.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerUpdate {
    private UUID id;
    private String natureOfBusiness;
    private String companyRepresentative;
}
