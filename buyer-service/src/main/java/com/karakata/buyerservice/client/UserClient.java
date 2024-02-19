package com.karakata.buyerservice.client;

import com.karakata.buyerservice.order.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "appuser-service", url = "http://localhost:9001", path = "/api/karakata/user")
public interface UserClient {
    @GetMapping("/findUserById")
    ResponseEntity<UserResponse> getUserById(@RequestParam("id") Long id);
}
