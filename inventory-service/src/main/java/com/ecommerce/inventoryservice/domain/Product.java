package com.ecommerce.inventoryservice.domain;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private String imageUrl;

    @ManyToOne
    private Category category;

}
