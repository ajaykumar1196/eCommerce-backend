package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;

public interface CartService {

    Cart findCartByUserId(Long userId);

    Cart createCart(Long userId);

    CartItem addItemToCart(Long userId, CartItem cartItem);
}
