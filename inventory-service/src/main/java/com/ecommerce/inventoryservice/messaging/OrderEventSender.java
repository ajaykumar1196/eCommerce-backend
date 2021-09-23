package com.ecommerce.inventoryservice.messaging;

import com.ecommerce.inventoryservice.dto.NewOrderEvent;
import com.ecommerce.inventoryservice.dto.StockConfirmationEvent;
import com.ecommerce.inventoryservice.dto.StockRejectionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@Configuration
public class OrderEventSender {

    private StreamBridge streamBridge;

    public OrderEventSender(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public static final String ORDER_STOCK_REJECT_TOPIC = "order.stock.reject";
    public static final String ORDER_STOCK_CONFIRM_TOPIC = "order.stock.confirm";

    public void sendStockReservationFailure(StockRejectionEvent stockRejectionEvent) {
        log.info("Rejecting product stock reservation for order {} for user {}", stockRejectionEvent.getOrderId(), stockRejectionEvent.getUserId());
        String key = String.format("%s-stock-reservation", stockRejectionEvent.getOrderId());
        Message<StockRejectionEvent> event = MessageBuilder.withPayload(stockRejectionEvent).setHeader(KafkaHeaders.MESSAGE_KEY, key).build();

        streamBridge.send(ORDER_STOCK_REJECT_TOPIC, event);
    }

    public void sendStockReservationSuccess(StockConfirmationEvent stockConfirmationEvent) {
        log.info("Rejecting product stock reservation for order {} for user {}", stockConfirmationEvent.getOrderId(), stockConfirmationEvent.getUserId());
        String key = String.format("%s-stock-release", stockConfirmationEvent.getOrderId());
        Message<StockConfirmationEvent> event = MessageBuilder.withPayload(stockConfirmationEvent).setHeader(KafkaHeaders.MESSAGE_KEY, key).build();

        streamBridge.send(ORDER_STOCK_CONFIRM_TOPIC, event);
    }


}
