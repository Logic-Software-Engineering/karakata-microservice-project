package com.karakata.sellerservice.sellerservice.product.client;

import com.karakata.sellerservice.sellerservice.product.dto.SellerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "appuser-service", url = "http://localhost:9001", path = "/api/karakata/seller")
public interface SellerClient {

    @GetMapping("findBySellerId")
    ResponseEntity<SellerResponse> getBySellerId(@RequestParam("id") Long id);
}
