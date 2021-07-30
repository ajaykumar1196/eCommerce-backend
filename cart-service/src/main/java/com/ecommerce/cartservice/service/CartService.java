package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.domain.Cart;

public interface CartService {

    Cart findCartByUserName(String userName);

    Cart createCart(String userName);
}
