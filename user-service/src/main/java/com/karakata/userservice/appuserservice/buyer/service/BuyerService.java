package com.karakata.userservice.appuserservice.buyer.service;





import com.karakata.userservice.appuserservice.buyer.model.Buyer;

import java.util.List;

public interface BuyerService {
    Buyer addBuyer(Buyer buyer);
    Buyer fetchBuyerByUsernameOrEmailOrMobile(String searchKey);
    List<Buyer> fetchAllBuyersOrByName(String searchKey,int pageNumber, int pageSize);
    Buyer editBuyer(Buyer buyer, Long id);
    void deleteBuyer(Long id);
}
