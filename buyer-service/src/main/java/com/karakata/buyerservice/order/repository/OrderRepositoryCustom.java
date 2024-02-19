package com.karakata.buyerservice.order.repository;


import com.karakata.buyerservice.order.model.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepositoryCustom {
    @Query("From Order o Where o.orderNumber=?1")
    List<Order> findByOrderNumber(String orderNumber, PageRequest pageRequest);

    @Query("From Order o Where o.userId=?1")
    List<Order> findOrderByUserId(Long id, PageRequest pageRequest);

    @Query("From Order o Where o.orderNumber=?1")
    boolean existsByOrderNumber(String orderNumber);

    @Modifying
    @Query("Delete From Order o Where o.orderNumber=?1")
    void deleteByOrderNumber(String orderNumber);
}
