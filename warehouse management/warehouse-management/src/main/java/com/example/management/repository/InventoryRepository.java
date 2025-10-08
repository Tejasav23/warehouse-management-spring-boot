package com.example.management.repository;

import com.example.management.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    boolean existsByProduct_id(Long id);
    boolean existsByWarehouse_id(Long id);
    List<Inventory> findAllByProduct_id(Long id);
}
