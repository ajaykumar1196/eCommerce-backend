package com.ecommerce.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StockConfirmationEvent {

    private Long orderId;
    private Long userId;
    private List<OrderItem> items;

}
