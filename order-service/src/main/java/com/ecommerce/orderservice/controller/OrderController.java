package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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
}
