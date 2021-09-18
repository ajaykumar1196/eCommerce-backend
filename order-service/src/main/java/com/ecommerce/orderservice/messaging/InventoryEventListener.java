package com.ecommerce.orderservice.messaging;

import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.domain.OrderItem;
import com.ecommerce.orderservice.dto.StockConfirmationEvent;
import com.ecommerce.orderservice.dto.StockRejectionEvent;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class InventoryEventListener {

    private final OrderService orderService;

    public InventoryEventListener(OrderService orderService) {
        this.orderService = orderService;
    }


    @Bean
    public Consumer<StockConfirmationEvent> confirmStock() {
        return stockConfirmationEvent -> {
            Long orderId = stockConfirmationEvent.getOrderId();
            Long userId = stockConfirmationEvent.getUserId();
            List<OrderItem> items = stockConfirmationEvent.getItems();

            Order order = orderService.findOrderByUserIdAndId(userId, orderId);
            order.setStatus(Order.RESERVED_PROCESSING_PAYMENT);
            order = orderService.updateStatus(order);
            orderService.saveOrderItems(order, items);
            // Process Payment
        };

    }

    @Bean
    public Consumer<StockRejectionEvent> rejectStock() {
        return  stockRejectionEvent -> {

            Long orderId = stockRejectionEvent.getOrderId();
            Long userId = stockRejectionEvent.getUserId();
            List<OrderItem> items = stockRejectionEvent.getItems();

            Order order = orderService.findOrderByUserIdAndId(userId, orderId);
            order.setStatus(Order.CANCELLED_OUT_OF_STOCK);
            order = orderService.updateStatus(order);
            orderService.saveOrderItems(order, items);

        };

    }

}
