package com.karakata.buyerservice.cartitem.service;

import com.karakata.buyerservice.cartitem.dto.ProductResponse;
import com.karakata.buyerservice.cartitem.exception.CartItemExceptionNotFound;
import com.karakata.buyerservice.cartitem.model.CartItem;
import com.karakata.buyerservice.cartitem.repository.CartItemRepository;
import com.karakata.buyerservice.client.ProductBuyerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductBuyerClient productBuyerClient;

    @Override
    public CartItem addItemToCart(CartItem cartItem) {
        cartItem.setCartCode("Cart".concat(String.valueOf(100000 + new Random().nextInt(Instant.now().getNano()))));
        ResponseEntity<ProductResponse> productResponse = productBuyerClient.getProductById(cartItem.getProductId());
        log.info("Product available quantity: {}", productResponse.getBody().getQuantity());
        if (productResponse.getBody().getQuantity() >= cartItem.getOrderQuantity()) {
            cartItem.setCartTotal(productResponse.getBody().getPrice()
                    .multiply(new BigDecimal(cartItem.getOrderQuantity())));
        } else {
            throw new CartItemExceptionNotFound("Product available quantity " + productResponse.getBody().getQuantity()
                    + " is less than cart quantity " + cartItem.getOrderQuantity());
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem fetchCartById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemExceptionNotFound("Cart ID " + id + " not found"));
    }

    @Override
    public CartItem fetchCartByCode(String cartCode) {
        return cartItemRepository.findByCartCode(cartCode)
                .orElseThrow(() -> new CartItemExceptionNotFound("Cart Code " + cartCode + " not found"));
    }

    @Override
    public List<CartItem> fetchAllCart(int pageNumber, int pageSize) {
        PageRequest page = PageRequest.of(pageNumber, pageSize);
        return cartItemRepository.findAll(page).toList();
    }

    @Override
    public BigDecimal calculateCartTotal(List<CartItem> cartItems) {
        BigDecimal cartSubTotal = BigDecimal.ZERO;
        BigDecimal singleCartAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            ResponseEntity<ProductResponse> productResponse = productBuyerClient.getProductById(cartItem.getProductId());
            if (!productResponse.getBody().getId().equals(cartItem.getProductId())) {
                throw new CartItemExceptionNotFound("Product ID " + cartItem.getProductId() +" not found");
            }
            if (productResponse.getBody().getId().equals(cartItem.getProductId())) {
                if (productResponse.getBody().getQuantity() >= cartItem.getOrderQuantity()) {
                    singleCartAmount = (productResponse.getBody().getPrice()
                            .multiply(new BigDecimal(cartItem.getOrderQuantity())))
                            .setScale(2, RoundingMode.UP);
                } else {
                    throw new CartItemExceptionNotFound("Request quantity " + cartItem.getOrderQuantity() +
                            " less than available stock quantity of " + productResponse.getBody().getQuantity());
                }
            }
            cartSubTotal = cartSubTotal.add(singleCartAmount);
            cartItem.setProductId(cartItem.getProductId());
            cartItem.setCartCode("Cart".concat(String.valueOf(10000 + new Random().nextInt(Instant.now().getNano()))));
            cartItem.setOrderQuantity(cartItem.getOrderQuantity());
            cartItem.setCartTotal(singleCartAmount);
        }

        return cartSubTotal;
    }

    @Override
    public CartItem editCart(CartItem cart, Long id) {

        CartItem savedCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemExceptionNotFound("Cart item with id " + id + " not found"));
        ResponseEntity<ProductResponse> productResponse = productBuyerClient.getProductById(savedCartItem.getProductId());
        if (Objects.nonNull(cart.getOrderQuantity()) && !"".equals(cart.getOrderQuantity())) {
            savedCartItem.setOrderQuantity(cart.getOrderQuantity());
        }
        savedCartItem.setCartTotal(productResponse.getBody().getPrice()
                .multiply(new BigDecimal(cart.getOrderQuantity())));
        return cartItemRepository.save(savedCartItem);
    }

    @Override
    public void deleteCart(Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
        }
    }
}
