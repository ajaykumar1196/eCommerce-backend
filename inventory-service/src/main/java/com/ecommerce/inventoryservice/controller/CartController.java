package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/me")
    public ResponseEntity<Cart> getMyCart(@AuthenticationPrincipal Long userId){
        log.info("Get user cart request from user {}", userId);
        Cart cart = cartService.findCartByUserId(userId);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createMyCart(@AuthenticationPrincipal Long userId){
        log.info("Create cart request from user {}", userId);
        Cart cart = cartService.createCart(userId);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);

    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@AuthenticationPrincipal Long userId, @RequestBody CartItem cartItem){
        log.info("Add item {} orders request from user {}", cartItem.getProduct().getName(), userId);
        CartItem newCartItem = cartService.addItemToCart(userId, cartItem);
        return new ResponseEntity<>(newCartItem, HttpStatus.CREATED);
    }
}
