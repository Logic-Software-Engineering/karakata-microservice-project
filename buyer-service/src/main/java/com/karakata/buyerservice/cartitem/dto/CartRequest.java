package com.karakata.buyerservice.cartitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    private String cartCode;
    private Integer orderQuantity;
    private Long productId;
    private BigDecimal cartTotal;
}
