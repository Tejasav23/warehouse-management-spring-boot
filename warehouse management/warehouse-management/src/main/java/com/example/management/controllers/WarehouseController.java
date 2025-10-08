package com.example.management.controllers;

import com.example.management.entities.Warehouse;
import com.example.management.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/warehouse")
public class
WarehouseController {
    @Autowired
    private WarehouseService service;

    @PostMapping("/addWarehouse")
    public String add(@RequestBody Warehouse entry){
        return service.addWarehouse(entry);
    }

    @DeleteMapping("/deleteWarehouse/{id}")
    public String delete(@PathVariable Long id){
        return service.deleteWarehouse(id);
    }

    @GetMapping("/getWarehouse/{name}")
    public ResponseEntity<Warehouse> get(@PathVariable String name) {
        return service.getWarehouseByName(name);
    }

    @GetMapping("/getWarehouses")
    public List<Warehouse> get(){
        return service.getAllWarehouse();
    }
}
