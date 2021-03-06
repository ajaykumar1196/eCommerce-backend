package com.ecommerce.orderservice.service;


import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.domain.OrderItem;
import com.ecommerce.orderservice.dto.NewOrderEvent;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    List<Order> findAllByUserId(Long userId, Pageable pageable);

    Order findOrderByUserIdAndId(Long userId, Long orderId);

    Order create(Order newOrder);

    Order updateStatus(Order order);

    void reserveProductStock(NewOrderEvent newOrderEvent);

    void saveOrderItems(Order order, List<OrderItem> items);
}
