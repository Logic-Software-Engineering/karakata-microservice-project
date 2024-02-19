package com.karakata.buyerservice.order.dto;

import com.karakata.buyerservice.cartitem.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private List<CartItem> cartItems = new ArrayList<>();
}
