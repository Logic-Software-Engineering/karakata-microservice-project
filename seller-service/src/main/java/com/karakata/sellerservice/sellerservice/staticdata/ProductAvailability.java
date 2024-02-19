package com.karakata.sellerservice.sellerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductAvailability {
    IN_STOCK("In stock"),
    OUT_OF_STOCK("Out of stock");

    private final String productAvailability;

    public String getProductAvailability() {
        return productAvailability;
    }
}
