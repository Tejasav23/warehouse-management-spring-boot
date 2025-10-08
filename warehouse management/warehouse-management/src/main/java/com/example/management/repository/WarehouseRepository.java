package com.example.management.repository;

import com.example.management.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    boolean existsByName(String name);
    Optional<Warehouse> findByName(String name);
}
