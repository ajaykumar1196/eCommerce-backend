package com.ecommerce.inventoryservice.service;


import com.ecommerce.inventoryservice.domain.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAllByCategory(String categoryName, Pageable pageable);

}
