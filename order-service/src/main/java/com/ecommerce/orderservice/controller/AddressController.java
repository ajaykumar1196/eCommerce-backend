package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.domain.Address;
import com.ecommerce.orderservice.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<Address>> getMyAddress(@AuthenticationPrincipal Long userId){
        log.info("Get addresses request by user {}", userId);
        List<Address> orders = addressService.findAllByUserId(userId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
