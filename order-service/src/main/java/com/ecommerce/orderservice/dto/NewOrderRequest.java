package com.ecommerce.orderservice.dto;


import com.ecommerce.orderservice.domain.Address;
import com.ecommerce.orderservice.domain.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewOrderRequest {

    private Address shippingAddressId;
    private PaymentDetails paymentDetailsId;

}
