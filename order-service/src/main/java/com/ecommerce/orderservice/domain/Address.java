package com.ecommerce.orderservice.domain;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "order_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Long userId;

    private String line1;
    private String line2;

    private String city;
    private String county;

    private String postcode;

    private String country;

}
