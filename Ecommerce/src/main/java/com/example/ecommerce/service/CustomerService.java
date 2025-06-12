package com.example.ecommerce.service;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public void register(Customer customer) {
        if (repo.findByEmail(customer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        repo.save(customer);
    }

    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(email);
        }
        return "Fail";
    }

    public Customer updateCustomerByEmail(String email, Customer updatedData) {
        Customer customer = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customer.setName(updatedData.getName());

        return repo.save(customer);
    }
}
