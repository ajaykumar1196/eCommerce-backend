package com.ecommerce.orderservice.service;


import com.ecommerce.orderservice.domain.PaymentDetails;

import java.util.List;

public interface PaymentDetailsService {

    List<PaymentDetails> findAllByUserId(Long userId);

}
