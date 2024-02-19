package com.karakata.buyerservice.client;

import com.karakata.buyerservice.cartitem.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "seller-service", url = "http://localhost:9002", path = "/api/karakata/product")
public interface ProductBuyerClient {
    @GetMapping("/findById")
    ResponseEntity<ProductResponse> getProductById(@RequestParam("id") Long id);

    @PutMapping("/reduceProductQuantity")
    ResponseEntity<String> reduceProductQuantity(@RequestParam("productId") Long productId,
                                                 @RequestParam("quantity") Integer quantity);
}
