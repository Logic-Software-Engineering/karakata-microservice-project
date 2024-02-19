package com.karakata.userservice.appuserservice.buyer.repository;


import com.karakata.userservice.appuserservice.buyer.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long>, BuyerRepositoryCustom {
}
