package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.domain.Cart;
import com.ecommerce.inventoryservice.domain.CartItem;
import com.ecommerce.inventoryservice.domain.Product;
import com.ecommerce.inventoryservice.dto.StockConfirmationEvent;
import com.ecommerce.inventoryservice.dto.StockRejectionEvent;
import com.ecommerce.inventoryservice.messaging.OrderEventSender;
import com.ecommerce.inventoryservice.repository.CartRepository;
import com.ecommerce.inventoryservice.service.CartItemService;
import com.ecommerce.inventoryservice.service.CartService;
import com.ecommerce.inventoryservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

    private final OrderEventSender orderEventSender;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService, OrderEventSender orderEventSender) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.orderEventSender = orderEventSender;
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
    public Boolean verifyIfInStock(CartItem cartItem) {
        Integer quantity = cartItem.getQuantity();
        Product product = cartItem.getProduct();
        return quantity >= product.getQuantity();
    }

    @Override
    public void reserveStock(CartItem cartItem){
        Product product = cartItem.getProduct();
        product.setQuantity(product.getQuantity() - cartItem.getQuantity());
        productService.save(product);
    }

    @Override
    public void rejectStockReservation(StockRejectionEvent stockRejectionEvent) {
        orderEventSender.sendStockReservationFailure(stockRejectionEvent);
    }

    @Override
    public void confirmStockReservation(StockConfirmationEvent stockConfirmationEvent) {
        orderEventSender.sendStockReservationSuccess(stockConfirmationEvent);
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
