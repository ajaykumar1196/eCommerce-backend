package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.domain.Product;
import com.ecommerce.inventoryservice.exception.CategoryNotFoundException;
import com.ecommerce.inventoryservice.repository.CategoryRepository;
import com.ecommerce.inventoryservice.repository.ProductRepository;
import com.ecommerce.inventoryservice.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAllByCategory(String categoryName, Pageable pageable) {

        categoryRepository.findCategoryByName(categoryName).orElseThrow(() -> new CategoryNotFoundException("Category '" + categoryName + "' does no exist"));

        return productRepository.getAllByCategoryName(categoryName, pageable);
    }
}
