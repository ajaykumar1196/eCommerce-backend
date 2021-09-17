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
    public List<Product> findAllByCategoryId(Long categoryId, Pageable pageable) {

        categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category '" + categoryId + "' does no exist"));

        return productRepository.getAllByCategoryId(categoryId, pageable);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
