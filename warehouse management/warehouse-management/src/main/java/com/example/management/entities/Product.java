package com.example.management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @Column(name = "category",nullable = false)
    private String category;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "description")
    private String description;

}
