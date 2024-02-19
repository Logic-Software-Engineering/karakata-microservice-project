package com.karakata.sellerservice.sellerservice.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReduceProductQuantity {
    private Long productId;
    private int quantity;
}
