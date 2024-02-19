package com.karakata.buyerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    DELIVERED("Delivered"),
    PENDING("Pending"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    PICKED("Picked"),
    RETURNED("Returned"),
    PLACED("Placed"),
    CANCELLED("Cancelled");

    private final String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
