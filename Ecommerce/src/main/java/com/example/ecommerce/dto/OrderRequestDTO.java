package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class OrderRequestDTO  {

    private Long productId;
    private int quantity;

}
