package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.domain.PaymentDetails;
import com.ecommerce.orderservice.service.PaymentDetailsService;
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
@RequestMapping("/api/payment-details")
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    public PaymentDetailsController(PaymentDetailsService paymentDetailsService) {
        this.paymentDetailsService = paymentDetailsService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<PaymentDetails>> getMyAddress(@AuthenticationPrincipal Long userId){
        log.info("Get payment details request by user {}", userId);
        List<PaymentDetails> orders = paymentDetailsService.findAllByUserId(userId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
