package com.karakata.authserver.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserType {
    ADMINISTRATOR("Administrator"),
    SELLER("Seller"),
    BUYER("Buyer");

    private final String userType;

    public String getUserType() {
        return userType;
    }
}
