package com.ecommerce.inventoryservice.exception;

import javax.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {

    public CategoryNotFoundException(String message){
        super(message);
    }

}
