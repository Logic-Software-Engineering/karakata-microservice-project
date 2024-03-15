package com.karakata.userservice.appuserservice.seller.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.karakata.userservice.appuserservice.events.event.AdminApprovalEvent;
import com.karakata.userservice.appuserservice.events.event.RegistrationEvent;
import com.karakata.userservice.appuserservice.events.event.SellerNotificationEvent;
import com.karakata.userservice.appuserservice.makerchecker.model.MakerChecker;
import com.karakata.userservice.appuserservice.makerchecker.service.MakerCheckerService;
import com.karakata.userservice.appuserservice.seller.dto.SellerApproval;
import com.karakata.userservice.appuserservice.seller.dto.SellerRequest;
import com.karakata.userservice.appuserservice.seller.dto.SellerResponse;
import com.karakata.userservice.appuserservice.seller.dto.SellerUpdate;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import com.karakata.userservice.appuserservice.seller.service.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/seller")
public record SellerController(SellerService sellerService, ModelMapper modelMapper,
                               ApplicationEventPublisher publisher,
                               MakerCheckerService makerCheckerService) {

    @PostMapping("/addSeller")
    public ResponseEntity<String> addSeller(@RequestBody SellerRequest sellerRequest, HttpServletRequest request) throws JsonProcessingException {
        Seller seller = modelMapper.map(sellerRequest, Seller.class);
        sellerService.addSeller(seller);
        publisher.publishEvent(new SellerNotificationEvent(applicationUrl(request), seller));
//        publisher.publishEvent(new AdminApprovalEvent(applicationUrl(request), ));
        return new ResponseEntity<>("Your registration is undergoing approval, an email will be sent to you once you" +
                " registration is approved to verify your email", HttpStatus.OK);
    }

    @PutMapping("/approveSellerRegistration")
    public ResponseEntity<String> approveSellerRegistration(@RequestBody SellerApproval sellerApproval,
                                                            HttpServletRequest request) throws JsonProcessingException {
        sellerService.approveProductCreation(sellerApproval.getMakerCheckerId(), sellerApproval.getAdminId(),
                sellerApproval.getRequestStatus());
        MakerChecker makerChecker = makerCheckerService.fetchMakerCheckerById(sellerApproval.getMakerCheckerId());
        Seller seller = sellerService.fetchSellerById(makerChecker.getEntityId());
        publisher.publishEvent(new RegistrationEvent(seller.getUser(), applicationUrl(request)));
        return new ResponseEntity<>("An email has been sent to your registered email address to verify your account",
                HttpStatus.OK);
    }

    @GetMapping("findBySellerId")
    public ResponseEntity<SellerResponse> getBySellerId(@RequestParam("id") Long id) {
        Seller seller = sellerService.fetchSellerById(id);
        SellerResponse sellerResponse = convertSellerToDto(seller);
        return new ResponseEntity<>(sellerResponse, HttpStatus.OK);
    }

    @GetMapping("/sellerByNameOrTaxId")
    public ResponseEntity<SellerResponse> getSellerByName(@RequestParam("searchKey") String searchKey) {
        Seller seller = sellerService.fetchSellerByNameOrTaxId(searchKey);
        SellerResponse sellerResponse = convertSellerToDto(seller);
        return new ResponseEntity<>(sellerResponse, HttpStatus.OK);
    }

    @GetMapping("/sellerByUsernameOrEmailOrMobile")
    public ResponseEntity<SellerResponse> getSellerByUsernameOrEmailOrMobile(@RequestParam("searchKey") String searchKey) {
        Seller seller = sellerService.fetchByUsernameOrEmailOrMobile(searchKey);
        SellerResponse sellerResponse = convertSellerToDto(seller);
        return new ResponseEntity<>(sellerResponse, HttpStatus.OK);
    }

    @GetMapping("/allSellers")
    public ResponseEntity<List<SellerResponse>> getAllSellers(@RequestParam("pageNumber") int pageNumber,
                                                              @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(sellerService.fetchAllSellers(pageNumber, pageSize)
                .stream().map(this::convertSellerToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/editSeller")
    public ResponseEntity<String> editSeller(@RequestBody SellerUpdate sellerUpdate,
                                             @RequestParam("makerCheckerId") String makerCheckerId,
                                             HttpServletRequest request) throws JsonProcessingException {
        Seller seller = modelMapper.map(sellerUpdate, Seller.class);
        sellerService.editSeller(makerCheckerId, seller);
        publisher.publishEvent(new SellerNotificationEvent(applicationUrl(request), seller));
//        publisher.publishEvent(new AdminApprovalEvent(applicationUrl(request), admin));
        return new ResponseEntity<>("Your update is awaiting approval", HttpStatus.OK);
    }

    @PutMapping("/approveSellerUpdate")
    public ResponseEntity<String> approveSellerUpdate(@RequestBody SellerApproval sellerApproval,
                                                      HttpServletRequest request) throws JsonProcessingException {
        sellerService.approveSellerUpdate(sellerApproval.getMakerCheckerId(), sellerApproval.getAdminId(),
                sellerApproval.getRequestStatus());

        //Create an email to send notification to seller for update
        MakerChecker makerChecker=makerCheckerService.fetchMakerCheckerById(sellerApproval.getMakerCheckerId());
        Seller seller=sellerService.fetchSellerById(makerChecker.getEntityId());
        publisher.publishEvent(new SellerNotificationEvent(applicationUrl(request), seller));
        return new ResponseEntity<>("You update has been approved", HttpStatus.OK);
    }

    @DeleteMapping("/deleteSeller")
    public ResponseEntity<String> deleteSeller(@RequestParam("id") String makerCheckerId) throws JsonProcessingException {
        sellerService.deleteSeller(makerCheckerId);
        return new ResponseEntity<>("Your delete operation is awaiting approval", HttpStatus.OK);
    }

    @PutMapping("approveSellerDeletion")
    public ResponseEntity<String> approveSellerDeletion(@RequestBody SellerApproval sellerApproval) throws JsonProcessingException {
        sellerService.approveSellerDeletion(sellerApproval.getMakerCheckerId(), sellerApproval.getAdminId(),
                sellerApproval.getRequestStatus());
        return new ResponseEntity<>("Seller deletion is approved", HttpStatus.OK);
    }


    private SellerResponse convertSellerToDto(Seller seller) {

        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setId(seller.getId());
        sellerResponse.setSellerName(seller.getSellerName());
        sellerResponse.setNatureOfBusiness(seller.getNatureOfBusiness());
        sellerResponse.setTaxId(seller.getTaxId());
        sellerResponse.setCompanyRepresentative(seller.getCompanyRepresentative());
        sellerResponse.setEmail(seller.getUser().getEmail());
        sellerResponse.setMobile(seller.getUser().getMobile());
        sellerResponse.setFullAddress(seller.getUser().getAddress().getFullAddress());
        sellerResponse.setLandmark(seller.getUser().getAddress().getLandmark());
        sellerResponse.setCity(seller.getUser().getAddress().getCity());
        sellerResponse.setState(seller.getUser().getAddress().getState());
        sellerResponse.setCountry(seller.getUser().getAddress().getCountry());
        return sellerResponse;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
