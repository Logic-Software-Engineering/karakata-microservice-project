package com.karakata.userservice.appuserservice.address.repository;


import com.karakata.userservice.appuserservice.address.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
