package com.karakata.userservice.appuserservice.buyer.exception;

public class BuyerNotFoundException extends RuntimeException {
    public BuyerNotFoundException() {
        super();
    }

    public BuyerNotFoundException(String message) {
        super(message);
    }
}
