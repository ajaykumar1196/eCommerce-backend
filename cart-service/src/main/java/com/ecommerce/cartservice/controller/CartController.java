package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.domain.Cart;
import com.ecommerce.cartservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/me")
    public ResponseEntity<Cart> getMyCart(@AuthenticationPrincipal String userName){

        System.out.println(userName);

        Cart cart = cartService.findCartByUserName(userName);

        Cart cartResponse =  Cart.builder().userName(userName).build();

        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createMyCart(@AuthenticationPrincipal String userName){

        Cart cart = cartService.createCart(userName);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);

    }
}
