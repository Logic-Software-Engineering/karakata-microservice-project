package com.karakata.buyerservice.cartitem.repository;

import com.karakata.buyerservice.cartitem.model.CartItem;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartItemRepositoryCustom {
    @Query("From CartItem c WHere c.cartCode=?1")
    Optional<CartItem> findByCartCode(String cartCode);
}
