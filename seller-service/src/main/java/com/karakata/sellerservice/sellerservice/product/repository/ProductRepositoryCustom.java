package com.karakata.sellerservice.sellerservice.product.repository;


import com.karakata.sellerservice.sellerservice.product.model.Product;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {
    @Query("FROM Product p WHERE p.productName Like %?1%")
    List<Product> findByProductName(String productName, Pageable pageable);

    @Query("FROM Product p WHERE p.productName=?1 AND p.price Between ?2 And ?3")
    List<Product> findProductNameBetweenPriceRange(String productName, BigDecimal price1,
                                               BigDecimal price2, Pageable pageable);

    @Query("FROM Product p WHERE p.productName=?1 And p.price >= ?2")
    List<Product> findProductByPriceWithinPriceLimit(String productName, BigDecimal price, Pageable pageable);

    @Query("From Product p Where p.category=?1")
    List<Product> findProductByCategory(ProductCategory productCategory, Pageable pageable);

    @Query("FROM Product p WHERE p.sellerId= ?1")
    List<Product> findProductBySeller(Long sellerId);
}
