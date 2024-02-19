package com.karakata.sellerservice.sellerservice.client;

import com.karakata.sellerservice.sellerservice.product.dto.AdminResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "admin-client", url = "http://localhost:9001", path = "/api/karakata/admin")
public interface AdminClient {
    @GetMapping("findAdminById")
    ResponseEntity<AdminResponse> getAdminById(@RequestParam("id") Long id);
}
