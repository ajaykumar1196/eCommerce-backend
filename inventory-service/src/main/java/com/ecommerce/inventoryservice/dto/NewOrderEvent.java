package com.ecommerce.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewOrderEvent {

    private Long id;
    private Long userId;

}
