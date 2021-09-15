package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.domain.PaymentDetails;
import com.ecommerce.orderservice.repository.PaymentDetailsRepository;
import com.ecommerce.orderservice.service.PaymentDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsServiceImpl(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Override
    public List<PaymentDetails> findAllByUserId(Long userId) {
        return paymentDetailsRepository.findAllByUserId(userId);
    }

}
