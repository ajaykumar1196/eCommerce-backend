package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.domain.Product;
import com.ecommerce.inventoryservice.dto.NewOrderEvent;
import com.ecommerce.inventoryservice.dto.StockConfirmationEvent;
import com.ecommerce.inventoryservice.dto.StockRejectionEvent;

public interface CartService {

    Cart findCartByUserId(Long userId);

    Boolean verifyIfInStock(CartItem cartItem);

    void reserveStock(CartItem cartItem);

    void rejectStockReservation(StockRejectionEvent stockRejectionEvent);

    void confirmStockReservation(StockConfirmationEvent stockConfirmationEvent);

    Cart createCart(Long userId);

    CartItem addItemToCart(Long userId, CartItem cartItem);
}
