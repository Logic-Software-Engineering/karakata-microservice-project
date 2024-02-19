package com.karakata.sellerservice.sellerservice.product.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponse {
    private Long id;
    private String sellerName;
    private String natureOfBusiness;
    private String companyRepresentative;
    private String taxId;
    private String email;
    private String mobile;
    private String fullAddress;
    private String landmark;
    private String city;
    private String state;
    private String country;
}
