package com.karakata.sellerservice.sellerservice.product.repository;

import com.karakata.sellerservice.sellerservice.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
