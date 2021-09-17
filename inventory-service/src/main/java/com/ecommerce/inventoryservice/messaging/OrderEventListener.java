package com.ecommerce.inventoryservice.messaging;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.dto.NewOrderEvent;
import com.ecommerce.inventoryservice.dto.OrderItem;
import com.ecommerce.inventoryservice.dto.StockConfirmationEvent;
import com.ecommerce.inventoryservice.dto.StockRejectionEvent;
import com.ecommerce.inventoryservice.service.CartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Configuration
public class OrderEventListener {

    private final CartService cartService;

    public OrderEventListener(CartService cartService) {
        this.cartService = cartService;
    }


    @Bean
    public Consumer<NewOrderEvent> reserveStock() {
        return newOrderEvent -> {
            Cart cart = cartService.findCartByUserId((newOrderEvent).getUserId());

            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cart.getCartItem()){
                orderItems.add(OrderItem.builder()
                        .productId(cartItem.getProduct().getId())
                        .price(cartItem.getProduct().getPrice())
                        .quantity(cartItem.getQuantity()).build());
            }

            for(CartItem cartItem : cart.getCartItem()){
                if(!cartService.verifyIfInStock(cartItem)){
                    cartService.rejectStockReservation(StockRejectionEvent.builder().orderId(newOrderEvent.getId()).userId(newOrderEvent.getUserId()).items(orderItems).build());
                }
            }

            for (CartItem cartItem : cart.getCartItem()){
                cartService.reserveStock(cartItem);
            }

            cartService.confirmStockReservation(StockConfirmationEvent.builder().orderId(newOrderEvent.getId()).userId(newOrderEvent.getUserId()).items(orderItems).build());

        };
    }

}
