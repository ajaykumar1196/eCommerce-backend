package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.domain.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

    List<PaymentDetails> findAllByUserId(Long userId);

}
