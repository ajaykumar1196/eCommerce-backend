package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getAllByCategoryName(String categoryName, Pageable pageable);

}
