package com.karakata.buyerservice.cartitem.exception;

public class CartItemExceptionNotFound extends RuntimeException{
    public CartItemExceptionNotFound() {
        super();
    }

    public CartItemExceptionNotFound(String message) {
        super(message);
    }
}
