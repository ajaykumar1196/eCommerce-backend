package com.ecommerce.orderservice.messaging;

import com.ecommerce.orderservice.domain.Order;
import com.ecommerce.orderservice.dto.NewOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@Configuration
public class InventoryEventSender {

    private StreamBridge streamBridge;

    public InventoryEventSender(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public static final String INVENTORY_STOCK_RESERVE_TOPIC = "inventory.stock.reserve";
    public static final String INVENTORY_STOCK_RELEASE_TOPIC = "inventory.stock.release";

    public void sendProductStockReservationEvent(NewOrderEvent newOrderEvent) {
        log.info("Reserving product stock for order {} by user {}", newOrderEvent.getOrderId(), newOrderEvent.getUserId());
        String key = String.format("%s-stock-reservation", newOrderEvent.getOrderId());
        Message<NewOrderEvent> event = MessageBuilder.withPayload(newOrderEvent).setHeader(KafkaHeaders.MESSAGE_KEY, key).build();
        streamBridge.send(INVENTORY_STOCK_RESERVE_TOPIC, event);
    }

    public void sendProductStockReleaseEvent(Order order) {
        log.info("Releasing product stock for order {}", order.getId());
        String key = String.format("%s-stock-release", order.getId());
        Message<Order> event = MessageBuilder.withPayload(order).setHeader(KafkaHeaders.MESSAGE_KEY, key).build();
        streamBridge.send(INVENTORY_STOCK_RELEASE_TOPIC, event);
    }

}
