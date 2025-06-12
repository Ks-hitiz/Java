package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CustomerLoginDTO;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Customer customer) {
        service.register(customer);
        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/login")
    public String login(@RequestBody CustomerLoginDTO dto) {
        return service.login(dto.getEmail(), dto.getPassword());
    }

    @PutMapping("/me")
    public ResponseEntity<Customer> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Customer updatedCustomer) {

        String email = userDetails.getUsername();
        Customer customer = service.updateCustomerByEmail(email, updatedCustomer);

        return ResponseEntity.ok(customer);
    }


}
