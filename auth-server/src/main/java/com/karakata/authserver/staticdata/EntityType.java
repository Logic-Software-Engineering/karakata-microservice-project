package com.karakata.authserver.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EntityType {
    PRODUCT("Product"),
    SELLER("Seller"),
    BUYER("Buyer"),
    ADMIN("Admin");

    private final String entityType;

    public String getEntityType() {
        return entityType;
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "entityType='" + entityType + '\'' +
                '}';
    }
}
