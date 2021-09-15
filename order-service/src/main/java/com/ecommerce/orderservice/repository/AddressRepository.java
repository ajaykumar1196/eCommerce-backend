package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.domain.Address;
import com.ecommerce.orderservice.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Order, Long> {

    List<Address> findAllByUserId(Long userId);

}
