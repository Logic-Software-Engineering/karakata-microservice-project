package com.karakata.buyerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductStatus {
    PENDING("Pending"),
    REJECTED("Rejected"),
    APPROVED("Approved");

    private final String productStatus;

    public String getProductStatus() {
        return productStatus;
    }

    @Override
    public String toString() {
        return "ProductStatus{" +
                "productStatus='" + productStatus + '\'' +
                '}';
    }
}
