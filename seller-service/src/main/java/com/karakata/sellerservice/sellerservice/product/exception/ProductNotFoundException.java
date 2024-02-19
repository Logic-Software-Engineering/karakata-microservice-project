package com.karakata.sellerservice.sellerservice.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
