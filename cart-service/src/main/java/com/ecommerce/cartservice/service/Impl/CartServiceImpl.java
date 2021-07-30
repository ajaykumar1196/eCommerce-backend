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
    public Cart findCartByUserName(String userName) {
        return cartRepository.findCartByUserName(userName);
    }

    @Override
    public Cart createCart(String userName) {

        Cart cart = cartRepository.findCartByUserName(userName);

        if(cart != null){
            return  cart;
        }

        cart = Cart.builder().userName(userName).build();
        return cartRepository.save(cart);
    }
}
