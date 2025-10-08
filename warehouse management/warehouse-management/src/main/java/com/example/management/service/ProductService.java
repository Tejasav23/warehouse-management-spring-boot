package com.example.management.service;

import com.example.management.entities.Product;
import com.example.management.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public String addProduct(Product entry){
        if(repository.existsByName(entry.getName())){
            return "Product already exists";
        }
        repository.save(entry);
        return "Product added successfully";
    }

    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    public List<Product> getProductByCategory(String category){
        return repository.findByCategory(category);
    }

    public ResponseEntity<Product> getProductByName(String name){
        Optional<Product> productoptional = repository.findByName(name);

        if(productoptional.isPresent()){
            Product product = productoptional.get();
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }
}
