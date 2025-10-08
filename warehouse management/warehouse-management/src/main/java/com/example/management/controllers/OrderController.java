package com.example.management.controllers;


import com.example.management.entities.Order;
import com.example.management.entities.enums.OrderStatus;
import com.example.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/order")
public class OrderController{
    @Autowired
    private OrderService service;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> add(@RequestBody Order entry){
        return service.addOrder(entry);
    }

    @DeleteMapping("/cancelOrder/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return service.deleteOrder(id);
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id){
        return service.getOrderById(id);
    }

    @PutMapping("/approveOrder/{id}")
    public ResponseEntity<String> updateStatusToApprove(@PathVariable Long id){
        return service.setStatusToApproved(id);
    }

    @PutMapping("/dispatchOrder/{id}")
    public ResponseEntity<String> updateStatusToDispatched(@PathVariable Long id){
        return service.setStatusToDispatched(id);
    }

    @PutMapping("/completeOrder/{id}")
    public ResponseEntity<String> updateStatusToCompleted(@PathVariable Long id){
        return service.setStatusToCompleted(id);
    }

    @GetMapping("/checkStatus/{id}")
    public ResponseEntity<OrderStatus> checkStatus(@PathVariable Long id){
        return service.checkStatus(id);
    }

}
