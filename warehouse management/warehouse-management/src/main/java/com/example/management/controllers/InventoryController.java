package com.example.management.controllers;


import com.example.management.entities.Inventory;
import com.example.management.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping("/addInventory")
    public ResponseEntity<String> add(@RequestBody Inventory entry){
        return service.createInventory(entry);
    }

    @GetMapping("/getInventory/{id}")
    public ResponseEntity<Inventory> get(@PathVariable Long id){
        return service.getInventoryById(id);
    }

    @PutMapping("/updateInventory/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Inventory entry){
        return service.updateInventory(id,entry);
    }

    @GetMapping("/getAllInventory")
    public List<Inventory> getAll(){
        return service.getAllInventory();
    }
}
