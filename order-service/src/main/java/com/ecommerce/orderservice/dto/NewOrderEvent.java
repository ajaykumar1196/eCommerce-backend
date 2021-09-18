package com.ecommerce.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewOrderEvent {

    private Long orderId;
    private Long userId;

}
