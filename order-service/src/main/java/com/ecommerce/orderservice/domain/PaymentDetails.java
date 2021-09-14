package com.ecommerce.orderservice.domain;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "payment_details")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Long userId;

    private String cardType;

    private String nameOnCard;

    private String cardNumber;

    private Integer cvv;

    private String expires;


}
