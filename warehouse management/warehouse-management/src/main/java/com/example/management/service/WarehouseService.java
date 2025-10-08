package com.example.management.service;

import com.example.management.entities.Warehouse;
import com.example.management.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository repository;

    public String addWarehouse(Warehouse entry){
            if(repository.existsByName(entry.getName()))return "warehouse already exists";
            repository.save(entry);
            return "warehouse added successfully";
    }

    public String deleteWarehouse(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "warehouse deleted successfully";
        }
        return "warehouse doesn't exists";
    }

    public ResponseEntity<Warehouse> getWarehouseByName(String name){
        Optional<Warehouse> warehouseOptional = repository.findByName(name);

        if (warehouseOptional.isPresent()) {
            Warehouse warehouse = warehouseOptional.get();
            return ResponseEntity.ok(warehouse);
        }
        return ResponseEntity.notFound().build();
    }

    public List<Warehouse> getAllWarehouse(){
        return repository.findAll();
    }
}
