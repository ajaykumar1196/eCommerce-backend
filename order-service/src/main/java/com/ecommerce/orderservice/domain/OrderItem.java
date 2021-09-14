package com.ecommerce.orderservice.domain;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Integer quantity;
    private Double price;

    private Long productId;

    @ManyToOne
    private Order orderId;

}
