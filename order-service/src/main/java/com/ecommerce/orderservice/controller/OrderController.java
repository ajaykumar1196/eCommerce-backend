package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.dto.NewOrderRequest;
import com.ecommerce.orderservice.dto.NewOrderResponse;
import com.ecommerce.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/me")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal Long userId, Pageable pageable){
        log.info("Get user orders request from user {}", userId);
        List<Order> orders = orderService.findAllByUserId(userId, pageable);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderByUserIdAndId(@AuthenticationPrincipal Long userId, @PathVariable Long orderId){
        log.info("Get user order with order id {} request from user {}", orderId, userId);
        Order order = orderService.findOrderByUserIdAndId(userId, orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<NewOrderResponse> newOrder(@AuthenticationPrincipal Long userId, @RequestBody NewOrderRequest newOrderRequest){
        log.info("New order request from user {}", userId);
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
