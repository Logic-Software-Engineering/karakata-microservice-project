package com.karakata.sellerservice.sellerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductCategory {
    APPLIANCES("Appliances"),
    ELECTRONICS("ELECTRONICS"),
    BOOKS("Books"),
    COMPUTING("Computing"),
    HEALTH_AND_BEAUTY("Health & Beauty"),
    HOME_AND_OFFICE("Home and Office"),
    PHONES_AND_TABLETS("Phones & Tables"),
    GAMING("Gaming"),
    SPORTING_GOODS("Sporting Goods"),
    FASHION("Fashion"),
    BABY_PRODUCTS("Barry Products"),
    IRRIGATION("Irrigation"),
    SEEDER("Seeder");

    private final String productCategory;

    public String getProductCategory() {
        return productCategory;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategory='" + productCategory + '\'' +
                '}';
    }
}
