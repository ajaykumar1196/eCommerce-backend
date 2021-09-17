package com.ecommerce.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private Integer quantity;
    private Double price;

    private Long productId;

}
