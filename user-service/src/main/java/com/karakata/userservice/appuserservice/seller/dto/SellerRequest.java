package com.karakata.userservice.appuserservice.seller.dto;


import com.karakata.userservice.appuserservice.appuser.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequest {
    private String sellerName;
    private String natureOfBusiness;
    private String companyRepresentative;
    private String taxId;
    private User user;
}
