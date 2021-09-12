package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.domain.Cart;
import com.ecommerce.cartservice.domain.CartItem;

public interface CartService {

    Cart findCartByUserId(Long userId);

    Cart createCart(Long userId);

    CartItem addItemToCart(Long userId, CartItem cartItem);
}
