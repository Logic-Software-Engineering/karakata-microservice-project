package com.karakata.buyerservice.order.dto;

import com.karakata.buyerservice.cartitem.model.CartItem;
import com.karakata.buyerservice.staticdata.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime expectedDeliveryDate;
    private Long userId;
    private BigDecimal vat;
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal orderSubTotal;
    private BigDecimal orderTotal;
}
