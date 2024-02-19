package com.karakata.sellerservice.sellerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductCondition {
    NEW("New"),
    USED("Used"),
    REFURBISHED("Refurbished"),
    FRESH("Fresh"),
    DRY("Dry");

    private final String productCondition;

    public String getProductCondition() {
        return productCondition;
    }
}
