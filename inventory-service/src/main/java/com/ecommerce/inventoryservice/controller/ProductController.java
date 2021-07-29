package com.ecommerce.inventoryservice.controller;


import com.ecommerce.inventoryservice.domain.Product;
import com.ecommerce.inventoryservice.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProductByCategory(@RequestParam("category") String categoryName, Pageable pageable){

        System.out.println(pageable);
        List<Product> products = productService.findAllByCategory(categoryName, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
