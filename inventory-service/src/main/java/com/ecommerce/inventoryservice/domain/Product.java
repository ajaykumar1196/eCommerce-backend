package com.ecommerce.inventoryservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Integer quantity;

    private Double price;
    private String imageUrl;

    @ManyToOne
    private Category category;

}
