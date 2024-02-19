package com.karakata.userservice.appuserservice.seller.repository;



import com.karakata.userservice.appuserservice.seller.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long>, SellerRepositoryCustom {
}
