package com.karakata.userservice.appuserservice.userverification.exception;

public class UserVerificationNotFoundException extends RuntimeException{
    public UserVerificationNotFoundException() {
        super();
    }

    public UserVerificationNotFoundException(String message) {
        super(message);
    }
}
