package com.ecommerce.cartservice.service.Impl;

import com.ecommerce.cartservice.domain.Cart;
import com.ecommerce.cartservice.repository.CartRepository;
import com.ecommerce.cartservice.service.CartItemService;
import com.ecommerce.cartservice.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    @Override
    public Cart findCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public Cart createCart(Long userId) {

        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart != null){
            return  cart;
        }

        cart = Cart.builder().userId(userId).build();
        return cartRepository.save(cart);
    }
}
