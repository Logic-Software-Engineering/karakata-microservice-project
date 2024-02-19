package com.karakata.sellerservice.sellerservice.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karakata.sellerservice.sellerservice.product.model.Product;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCategory;
import com.karakata.sellerservice.sellerservice.staticdata.RequestStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void addProduct(Product product) throws JsonProcessingException;

    Product fetchProductById(Long id);

    List<Product> fetchAllProductOrByName(String productName, int pageNumber, int pageSize);

    List<Product> fetchProductByPriceLimit(String productName, BigDecimal price, int pageNumber, int pageSize);

    List<Product> fetchProductBetweenPriceRange(String productName, BigDecimal lowerPrice, BigDecimal upperPrice,
                                                int pageNumber, int pageSize);

    List<Product> fetchProductByCategory(ProductCategory productCategory, int pageNumber, int pageSize);

    List<Product> fetchProductsBySeller(Long sellerId);

    void approveProductCreation(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException;

    void editProduct(String makerCheckerId, Product product) throws JsonProcessingException;

    void approveProductUpdate(String makerCheckerId, Long adminId, RequestStatus requestStatus) throws JsonProcessingException;

    void reduceProductQuantity(Long productId, Integer quantity);

    void deleteProduct(String makerCheckerId) throws JsonProcessingException;

    void approveDeleteProduct(String makerCheckerId, Long adminId, RequestStatus requestStatus) throws JsonProcessingException;

}
