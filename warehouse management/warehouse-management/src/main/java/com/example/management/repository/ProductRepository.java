package com.example.management.repository;

import com.example.management.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);
    List<Product> findByCategory(String category);
    Optional<Product> findByName(String name);
}
