package com.ecommerce.orderservice.service;


import com.ecommerce.orderservice.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    List<Order> findAllByUserId(Long userId, Pageable pageable);
    Order findOrderByUserIdAndId(Long userId, Long orderId);
}
