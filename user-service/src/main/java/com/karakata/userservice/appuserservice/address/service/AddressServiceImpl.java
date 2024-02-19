package com.karakata.userservice.appuserservice.address.service;


import com.karakata.userservice.appuserservice.address.exception.AddressNotFoundException;
import com.karakata.userservice.appuserservice.address.model.Address;
import com.karakata.userservice.appuserservice.address.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public Address fetchAddress(Long id) {

        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address " + id + " not found"));
    }

    @Override
    public List<Address> fetchAllAddresses(int pageNumber, int pageSize) {
        return addressRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }

    @Override
    public Address editAddress(Address address, Long id) {
        Address savedAddress = addressRepository.findById(id).
                orElseThrow(() -> new AddressNotFoundException("Address " + id + " not found"));
        if (Objects.nonNull(address.getFullAddress()) && !"".equalsIgnoreCase(address.getFullAddress())) {
            savedAddress.setFullAddress(address.getFullAddress());
        }
        if (Objects.nonNull(address.getLandmark()) && !"".equalsIgnoreCase(address.getLandmark())) {
            savedAddress.setLandmark(address.getLandmark());
        }
        if (Objects.nonNull(address.getCity()) && !"".equalsIgnoreCase(address.getCity())) {
            savedAddress.setCity(address.getCity());
        }
        if (Objects.nonNull(address.getState()) && !"".equalsIgnoreCase(address.getState())) {
            savedAddress.setState(address.getState());
        }
        if (Objects.nonNull(address.getCountry()) && !"".equalsIgnoreCase(address.getCountry())) {
            savedAddress.setCountry(address.getCountry());
        }
        return addressRepository.save(savedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
        }
    }


}
