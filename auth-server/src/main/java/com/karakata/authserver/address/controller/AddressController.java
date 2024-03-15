package com.karakata.authserver.address.controller;



import com.karakata.authserver.address.dto.AddressRequest;
import com.karakata.authserver.address.dto.AddressResponse;
import com.karakata.authserver.address.dto.AddressUpdate;
import com.karakata.authserver.address.model.Address;
import com.karakata.authserver.address.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/karakata/address")
public record AddressController(AddressService addressService, ModelMapper modelMapper) {

    @PostMapping("/saveAddress")
    public ResponseEntity<String> saveAddress(@RequestBody AddressRequest addressRequest) {
        Address address=modelMapper.map(addressRequest,Address.class);
        addressService.saveAddress(address);
        return new ResponseEntity<>("Address created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/addressById")
    public ResponseEntity<AddressResponse> getAddress(@RequestParam("addressId") Long addressId) {
        Address address= addressService.fetchAddress(addressId);
        AddressResponse addressResponse = convertToDto(address);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @GetMapping("/addAddresses")
    public ResponseEntity<List<AddressResponse>> getAllAddresses(@RequestParam("pageNumber") int pageNumber,
                                                                 @RequestParam("pageSize") int pageSize) {

        List<AddressResponse> addressResponses = addressService.fetchAllAddresses(pageNumber, pageSize)
                .stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(addressResponses,HttpStatus.OK);
    }

    @PutMapping("/editAddress")
    public ResponseEntity<String> editAddress(@RequestBody AddressUpdate addressUpdate,
                                                     @RequestParam("addressId") Long id) {
        Address address = modelMapper.map(addressUpdate, Address.class);
        Address post = addressService.editAddress(address, id);
        AddressUpdate posted = modelMapper.map(post, AddressUpdate.class);
        return new ResponseEntity<>("Address updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddressById")
    public ResponseEntity<String> deleteAddress(@RequestParam("addressId") Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>("Address deleted "+id+" successfully", HttpStatus.NO_CONTENT);
    }


    private AddressResponse convertToDto(Address address){
        AddressResponse addressResponse=new AddressResponse();
        addressResponse.setAddress_id(address.getId());
        addressResponse.setFullAddress(address.getFullAddress());
        addressResponse.setLandmark(address.getLandmark());
        addressResponse.setCity(address.getCity());
        addressResponse.setState(address.getState());
        addressResponse.setCountry(address.getCountry());
        return addressResponse;
    }
}
