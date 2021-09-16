package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.dto.NewOrderEvent;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.messaging.InventoryEventSender;
import com.ecommerce.orderservice.repository.OrderItemRepository;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final InventoryEventSender inventoryEventSender;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, InventoryEventSender inventoryEventSender) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.inventoryEventSender = inventoryEventSender;
    }

    @Override
    public List<Order> findAllByUserId(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Order findOrderByUserIdAndId(Long userId, Long orderId) {
        return orderRepository.findOrderByUserIdAndId(userId, orderId).orElseThrow(() -> new OrderNotFoundException("Order '" + orderId + "' does no exist"));
    }

    @Override
    public Order create(Order newOrder) {
        Order order = orderRepository.save(newOrder);

        NewOrderEvent newOrderEvent = NewOrderEvent.builder().id(order.getId()).userId(order.getUserId()).build();
        reserveProductStock(newOrderEvent);

        return order;
    }

    public void reserveProductStock(NewOrderEvent newOrderEvent) {
        inventoryEventSender.sendProductStockReservationEvent(newOrderEvent);
    }
}
