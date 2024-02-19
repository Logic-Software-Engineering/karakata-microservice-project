package com.karakata.buyerservice.order.dto;

import com.karakata.buyerservice.staticdata.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdate {
    private OrderStatus orderStatus;
    private LocalDateTime expectedDeliverDate;
}
