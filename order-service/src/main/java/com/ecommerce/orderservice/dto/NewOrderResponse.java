package com.ecommerce.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewOrderResponse {

    private Long id;
    private String status;

}
