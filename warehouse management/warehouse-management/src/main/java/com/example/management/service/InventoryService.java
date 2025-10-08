package com.example.management.service;


import com.example.management.entities.Inventory;
import com.example.management.entities.Product;
import com.example.management.entities.Warehouse;
import com.example.management.repository.InventoryRepository;
import com.example.management.repository.ProductRepository;
import com.example.management.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public  ResponseEntity<String > createInventory(Inventory entry){
        Optional<Product> productOptional = productRepository.findByName(entry.getProduct().getName());
        Optional<Warehouse> warehouseOptional = warehouseRepository.findByName(entry.getWarehouse().getName());
        if(productOptional.isEmpty() || warehouseOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        Warehouse warehouse = warehouseOptional.get();
        if(repository.existsByProduct_id(product.getId()) && repository.existsByWarehouse_id(warehouse.getId())){
            return ResponseEntity.badRequest().body("Inventory exists");
        }
        entry.setProduct(product);
        entry.setWarehouse(warehouse);
        repository.save(entry);
        return ResponseEntity.ok("Inventory added successfully");
    }

    public ResponseEntity<Inventory> getInventoryById(Long id){
        Optional<Inventory> inventoryOptional = repository.findById(id);
        if(inventoryOptional.isPresent()){
            return ResponseEntity.ok(inventoryOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> updateInventory(Long id, Inventory entry) {
        Optional<Inventory> optionalInventory = repository.findById(id);

        if (optionalInventory.isPresent()) {
            Inventory existingInventory = optionalInventory.get();

            existingInventory.setProduct(entry.getProduct());
            existingInventory.setStock(entry.getStock());
            existingInventory.setWarehouse(entry.getWarehouse());

            repository.save(existingInventory);
            return ResponseEntity.ok("Inventory updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public List<Inventory> getAllInventory(){
        return repository.findAll();
    }

}
