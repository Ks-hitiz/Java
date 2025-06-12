package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderRequestDTO;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(
            @RequestBody OrderRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Order order = service.placeOrder(dto.getProductId(), dto.getQuantity(), userDetails);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersForUser(@AuthenticationPrincipal UserDetails userDetails) {
        List<Order> orders = service.listOrdersForAuthenticatedUser(userDetails);
        return ResponseEntity.ok(orders);
    }
}
