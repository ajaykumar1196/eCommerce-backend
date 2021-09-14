package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.repository.CartItemRepository;
import com.ecommerce.inventoryservice.service.CartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem saveCartItem(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }
}
