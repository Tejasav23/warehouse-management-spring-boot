package com.example.management.controllers;

import com.example.management.entities.Product;
import com.example.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SuppressWarnings("unused")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public String add(@RequestBody Product entry){
        return service.addProduct(entry);
    }

    @GetMapping("/getAllProduct")
    public List<Product> getAll(){
        return service.getAllProduct();
    }

    @GetMapping("/getProduct/{name}")
    public ResponseEntity<Product> get(@PathVariable String name){
        return service.getProductByName(name);
    }

    @GetMapping("/getProducts/{category}")
    public List<Product> getByCategory(@PathVariable String category){
        return service.getProductByCategory(category);
    }
}
