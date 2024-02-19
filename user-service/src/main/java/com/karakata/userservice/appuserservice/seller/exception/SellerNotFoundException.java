package com.karakata.userservice.appuserservice.seller.exception;

public class SellerNotFoundException extends RuntimeException{
    public SellerNotFoundException() {
        super();
    }

    public SellerNotFoundException(String message) {
        super(message);
    }
}
