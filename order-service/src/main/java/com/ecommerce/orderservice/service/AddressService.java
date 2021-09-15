package com.ecommerce.orderservice.service;


import com.ecommerce.orderservice.domain.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAllByUserId(Long userId);

}
