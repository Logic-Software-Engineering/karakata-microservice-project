package com.karakata.userservice.appuserservice.seller.repository;



import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepositoryCustom {
    @Query("From Seller s Where s.sellerName=?1 OR s.taxId=?2")
    Optional<Seller> findBySellerNameOrTaxId(String sellerName, String taxId);

    @Query("From Seller s Where s.user=?1")
    Seller findByUserNameOrEmailOrMobile(User user);
}
