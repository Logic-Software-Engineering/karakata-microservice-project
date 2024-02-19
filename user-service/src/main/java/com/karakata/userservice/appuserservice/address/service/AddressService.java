package com.karakata.userservice.appuserservice.address.service;




import com.karakata.userservice.appuserservice.address.model.Address;

import java.util.List;

public interface AddressService {
    void saveAddress(Address address);
    Address fetchAddress(Long id);
    List<Address> fetchAllAddresses(int pageNumber, int pageSize);
    Address editAddress(Address address, Long id);
    void deleteAddress(Long id);
}
