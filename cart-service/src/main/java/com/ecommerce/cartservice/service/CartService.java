package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.domain.Cart;

public interface CartService {

    Cart findCartByUserId(Long userId);

    Cart createCart(Long userId);
}
