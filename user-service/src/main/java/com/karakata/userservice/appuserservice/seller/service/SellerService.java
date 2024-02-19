package com.karakata.userservice.appuserservice.seller.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import com.karakata.userservice.appuserservice.staticdata.RequestStatus;

import java.util.List;

public interface SellerService {
    void addSeller(Seller seller) throws JsonProcessingException;

    void approveProductCreation(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException;

    Seller fetchSellerById(Long id);

    Seller fetchSellerByNameOrTaxId(String searchKey);

    Seller fetchByUsernameOrEmailOrMobile(String searchKey);

    List<Seller> fetchAllSellers(int pageNumber, int pageSize);

    void editSeller(String makerCheckerId, Seller seller) throws JsonProcessingException;

    void approveSellerUpdate(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException;

    void deleteSeller(String makerCheckerId) throws JsonProcessingException;

    void approveSellerDeletion(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException;
}
