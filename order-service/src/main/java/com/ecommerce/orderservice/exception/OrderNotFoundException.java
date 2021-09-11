package com.ecommerce.orderservice.exception;

import javax.persistence.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(String message){
        super(message);
    }

}
