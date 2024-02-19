package com.karakata.userservice.appuserservice.userpasswordmanagement.exception;

public class UserPasswordVerificationNotFoundException extends RuntimeException{
    public UserPasswordVerificationNotFoundException() {
        super();
    }

    public UserPasswordVerificationNotFoundException(String message) {
        super(message);
    }
}
