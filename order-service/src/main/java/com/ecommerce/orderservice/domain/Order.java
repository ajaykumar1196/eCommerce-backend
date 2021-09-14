package com.ecommerce.orderservice.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String status;
    private Long totalAmount;
    private Date createdAt;
    private Date updatedAt;

    private Long userId;
    private Long paymentId;


    @ManyToOne
    private Address shippingAddressId;

    @ManyToOne
    private PaymentDetails paymentDetailsId;
}
