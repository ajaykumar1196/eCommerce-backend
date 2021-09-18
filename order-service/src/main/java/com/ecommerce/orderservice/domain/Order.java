package com.ecommerce.orderservice.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders_table")
public class Order {

    public static final String INITIATED_RESERVING_STOCK = "INITIATED_RESERVING_STOCK";
    public static final String CANCELLED_OUT_OF_STOCK = "CANCELLED_OUT_OF_STOCK";
    public static final String RESERVED_PROCESSING_PAYMENT = "RESERVED_PROCESSING_PAYMENT";

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
