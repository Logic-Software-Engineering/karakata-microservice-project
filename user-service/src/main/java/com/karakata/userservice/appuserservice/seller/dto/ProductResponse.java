package com.karakata.userservice.appuserservice.seller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private UUID id;
    private String sku;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private Integer quantity;
}
