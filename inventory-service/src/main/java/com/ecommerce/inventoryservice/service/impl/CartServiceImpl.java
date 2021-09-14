package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.repository.CartRepository;
import com.ecommerce.inventoryservice.service.CartItemService;
import com.ecommerce.inventoryservice.service.CartService;
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

        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart == null){
            cart = createCart(userId);
        }

        return cart;
    }

    @Override
    public Cart createCart(Long userId) {

        Cart cart = Cart.builder().userId(userId).build();

        return cartRepository.save(cart);
    }

    @Override
    public CartItem addItemToCart(Long userId, CartItem cartItem) {

        Cart cart = findCartByUserId(userId);
        cartItem.setCart(cart);

        return cartItemService.saveCartItem(cartItem);
    }
}
