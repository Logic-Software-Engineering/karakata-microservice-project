package com.karakata.userservice.appuserservice.buyer.controller;


import com.karakata.userservice.appuserservice.buyer.dto.BuyerRequest;
import com.karakata.userservice.appuserservice.buyer.dto.BuyerResponse;
import com.karakata.userservice.appuserservice.buyer.dto.BuyerUpdate;
import com.karakata.userservice.appuserservice.buyer.model.Buyer;
import com.karakata.userservice.appuserservice.buyer.service.BuyerService;
import com.karakata.userservice.appuserservice.events.event.RegistrationEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/buyer")
public record BuyerController(BuyerService buyerService, ModelMapper modelMapper,
                              ApplicationEventPublisher publisher) {

    @PostMapping("/addBuyer")
    public ResponseEntity<String> addBuyer(@RequestBody BuyerRequest buyerRequest, HttpServletRequest request) {
        Buyer buyer = modelMapper.map(buyerRequest, Buyer.class);
        Buyer post = buyerService.addBuyer(buyer);
        BuyerRequest posted = modelMapper.map(post, BuyerRequest.class);
        publisher.publishEvent(new RegistrationEvent(post.getUser(), applicationUrl(request)));
        return new ResponseEntity<>("An email has been sent to your registered email address to verify your account",
                HttpStatus.OK);
    }

    @GetMapping("/findBuyer")
    public ResponseEntity<BuyerResponse> getBuyer(@RequestParam("searchKey") String searchKey) {
        Buyer buyer = buyerService.fetchBuyerByUsernameOrEmailOrMobile(searchKey);
        BuyerResponse response = convertBuyerToDto(buyer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findAllBuyersOrByName")
    public ResponseEntity<List<BuyerResponse>> getAllUserOrByName(@RequestParam(value = "searchKey", required = false,
                                                                  defaultValue = "") String searchKey,
                                                                  @RequestParam("pageNumber") int pageNumber,
                                                                  @RequestParam("pageSize") int pageSize) {

        return new ResponseEntity<>(buyerService.fetchAllBuyersOrByName(searchKey, pageNumber, pageSize)
                .stream().map(this::convertBuyerToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/editBuyer")
    public ResponseEntity<BuyerUpdate> updateBuyer(@RequestBody BuyerUpdate buyerUpdate,
                                                   @RequestParam("id") Long id) {
        Buyer buyer = modelMapper.map(buyerUpdate, Buyer.class);
        Buyer post = buyerService.editBuyer(buyer, id);
        BuyerUpdate posted = modelMapper.map(post, BuyerUpdate.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBuyer")
    public ResponseEntity<String> deleteBuyer(@RequestParam("id") Long id) {
        buyerService.deleteBuyer(id);
        return new ResponseEntity<>("Buyer deleted successfully", HttpStatus.OK);
    }

    private BuyerResponse convertBuyerToDto(Buyer buyer) {
        BuyerResponse buyerResponse = new BuyerResponse();
        buyerResponse.setFirstName(buyer.getFirstName());
        buyerResponse.setLastName(buyer.getLastName());
        buyerResponse.setGender(buyer.getGender());
        buyerResponse.setUser(buyer.getUser());
        return buyerResponse;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
