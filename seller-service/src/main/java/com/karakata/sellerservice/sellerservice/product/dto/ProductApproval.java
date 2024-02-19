package com.karakata.sellerservice.sellerservice.product.dto;


import com.karakata.sellerservice.sellerservice.staticdata.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductApproval {
    private String makerCheckerId;
    private Long adminId;
    private RequestStatus requestStatus;
}
