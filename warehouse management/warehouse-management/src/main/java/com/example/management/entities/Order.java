package com.example.management.entities;

import com.example.management.entities.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("unused")
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id",nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
