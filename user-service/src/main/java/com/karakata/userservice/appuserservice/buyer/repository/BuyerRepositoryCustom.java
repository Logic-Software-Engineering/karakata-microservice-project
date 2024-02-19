package com.karakata.userservice.appuserservice.buyer.repository;


import com.karakata.userservice.appuserservice.appuser.model.User;
import com.karakata.userservice.appuserservice.buyer.model.Buyer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyerRepositoryCustom {
    @Query("From Buyer b Where b.firstName=?1 Or b.lastName=?2")
    List<Buyer> findByName(String firstName, String lastName, PageRequest pageRequest);

    @Query("From Buyer b Where b.user=?1")
    Buyer findByUser(User user);
}
