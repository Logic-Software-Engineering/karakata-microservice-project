package com.karakata.userservice.appuserservice.seller.dto;

import com.karakata.userservice.appuserservice.staticdata.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerApproval {
    private String makerCheckerId;
    private Long adminId;
    private RequestStatus requestStatus;
}
