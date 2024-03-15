package com.karakata.authserver.address.repository;



import com.karakata.authserver.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
