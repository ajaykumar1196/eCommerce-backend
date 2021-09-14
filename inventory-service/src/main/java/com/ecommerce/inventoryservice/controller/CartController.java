package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/me")
    public ResponseEntity<Cart> getMyCart(@AuthenticationPrincipal Long userId){

        Cart cart = cartService.findCartByUserId(userId);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createMyCart(@AuthenticationPrincipal Long userId){

        Cart cart = cartService.createCart(userId);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);

    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@AuthenticationPrincipal Long userId, @RequestBody CartItem cartItem){
        CartItem newCartItem = cartService.addItemToCart(userId, cartItem);
        return new ResponseEntity<>(newCartItem, HttpStatus.CREATED);
    }
}
