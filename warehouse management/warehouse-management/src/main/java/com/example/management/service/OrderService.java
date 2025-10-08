package com.example.management.service;



import com.example.management.entities.Inventory;
import com.example.management.entities.Order;
import com.example.management.entities.Product;
import com.example.management.entities.enums.OrderStatus;
import com.example.management.repository.InventoryRepository;
import com.example.management.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings("unused")
@Service
public class OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private OrderRepository repository;

    public ResponseEntity<String> addOrder(Order entry){
        ResponseEntity<Product> productResponseEntity = productService.getProductByName(entry.getProduct().getName());
        if(productResponseEntity.hasBody()){
            Product p = productResponseEntity.getBody();
            if (p == null) return ResponseEntity.badRequest().body("product not found");

            List<Inventory> inventoryList = inventoryRepository.findAllByProduct_id(p.getId());
            if(inventoryList.isEmpty()){
                return ResponseEntity.badRequest().body("Out of stock");
            }
            Collections.sort(inventoryList, new Comparator<Inventory>() {
                @Override
                public int compare(Inventory o1, Inventory o2) {
                    return Integer.compare(o2.getStock(), o1.getStock());
                }
            });
            if(entry.getQuantity()<=inventoryList.get(0).getStock()){
                entry.setStatus(OrderStatus.PENDING);
                entry.setInventory(inventoryList.get(0));
                entry.setProduct(p);
                Inventory inventory = inventoryList.get(0);
                inventory.setStock(inventory.getStock()-entry.getQuantity());
                inventoryRepository.save(inventory);
                repository.save(entry);
                return ResponseEntity.ok("order placed Successfully");
            }
            return ResponseEntity.badRequest().body("Out of stock");
        }
        return ResponseEntity.badRequest().body("product not found");
    }

    public ResponseEntity<String> deleteOrder(Long id){
        Optional<Order> orderOptional = repository.findById(id);
        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getStatus() == OrderStatus.COMPLETED)
                return ResponseEntity.badRequest().body("Order already completed");

            Inventory inventory = order.getInventory();
            inventory.setStock(order.getQuantity() + inventory.getStock());
            order.setStatus(OrderStatus.CANCELLED);
            inventoryRepository.save(inventory);
            repository.save(order);
            return ResponseEntity.ok("Order cancelled");
        }
        return  ResponseEntity.notFound().build();
    }

    public ResponseEntity<Order> getOrderById(Long id){
        Optional<Order> orderOptional = repository.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> setStatusToApproved(Long id){
        Optional<Order> orderOptional = repository.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            OrderStatus currentStatus = order.getStatus();
            if(currentStatus == OrderStatus.PENDING){
                order.setStatus(OrderStatus.APPROVED);
                repository.save(order);
                return ResponseEntity.ok("ORDER APPROVED");
            }
            else{
                return ResponseEntity.badRequest().body("Cannot change order status once it is " + currentStatus);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> setStatusToCompleted(Long id){
        Optional<Order> orderOptional = repository.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            OrderStatus currentStatus = order.getStatus();
            if(currentStatus == OrderStatus.DISPATCHED){
                order.setStatus(OrderStatus.COMPLETED);
                repository.save(order);
                return ResponseEntity.ok("ORDER COMPLETED");
            }
            else if(currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.COMPLETED){
                return ResponseEntity.badRequest().body("Cannot change order status once it is " + currentStatus);
            }
            else{
                return ResponseEntity.badRequest().body("Order Not DISPATCHED");
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> setStatusToDispatched(Long id){
        Optional<Order> orderOptional = repository.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            OrderStatus currentStatus = order.getStatus();
            if(currentStatus == OrderStatus.APPROVED){
                order.setStatus(OrderStatus.DISPATCHED);
                repository.save(order);
                return ResponseEntity.ok("ORDER DISPATCHED");
            }
            else{
                return ResponseEntity.badRequest().body("Cannot change order status once it is " + currentStatus);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<OrderStatus> checkStatus(Long id){
        Optional<Order> orderOptional = repository.findById(id);

        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            return ResponseEntity.ok(order.getStatus());
        }
        return ResponseEntity.notFound().build();
    }

}
