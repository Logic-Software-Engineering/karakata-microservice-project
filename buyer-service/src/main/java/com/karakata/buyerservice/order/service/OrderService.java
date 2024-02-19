package com.karakata.buyerservice.order.service;

import com.karakata.buyerservice.order.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    Order fetchOrderById(Long id);

    List<Order> fetchByUser(Long userId, int pageNumber, int pageSize);

    List<Order> fetchOrderNumber(String searchKey, int pageNumber, int pageSize);

    Order editOrder(Order order, Long id);

    void cancelOrder(Long orderId);
}
