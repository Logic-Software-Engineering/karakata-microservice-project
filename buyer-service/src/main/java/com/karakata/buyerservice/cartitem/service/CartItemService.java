package com.karakata.buyerservice.cartitem.service;

import com.karakata.buyerservice.cartitem.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {
    CartItem addItemToCart(CartItem cartItem);
    CartItem fetchCartById(Long id);
    CartItem fetchCartByCode(String cartCode);
    List<CartItem> fetchAllCart(int pageNumber, int pageSize);
    BigDecimal calculateCartTotal(List<CartItem> cartItems);
    CartItem editCart(CartItem cart, Long id);
    void deleteCart(Long id);
}
