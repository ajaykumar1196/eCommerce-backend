package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.dto.NewOrderRequest;
import com.ecommerce.orderservice.dto.NewOrderResponse;
import com.ecommerce.orderservice.messaging.InventoryEventSender;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final InventoryEventSender inventoryEventSender;


    public OrderController(OrderService orderService, InventoryEventSender inventoryEventSender) {
        this.orderService = orderService;
        this.inventoryEventSender = inventoryEventSender;
    }


    @GetMapping("/me")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal Long userId, Pageable pageable){

        List<Order> orders = orderService.findAllByUserId(userId, pageable);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderByUserIdAndId(@AuthenticationPrincipal Long userId, @PathVariable Long orderId){

        Order order = orderService.findOrderByUserIdAndId(userId, orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<NewOrderResponse> newOrder(@AuthenticationPrincipal Long userId, @RequestBody NewOrderRequest newOrderRequest){

        Order newOrder = Order.builder()
                .userId(userId)
                .updatedAt(new Date())
                .createdAt(new Date())
                .shippingAddressId(newOrderRequest.getShippingAddressId())
                .paymentDetailsId(newOrderRequest.getPaymentDetailsId())
                .status(Order.INITIATED_RESERVING_STOCK)
                .build();

        Order order = orderService.create(newOrder);

        NewOrderResponse newOrderResponse = NewOrderResponse.builder().id(order.getId()).status(order.getStatus()).build();

        return new ResponseEntity<>(newOrderResponse, HttpStatus.CREATED);
    }
}
