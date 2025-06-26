package com.training.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Right {
    private String id;
    private boolean opponent;
    private Classification classification;
    private String name;
    private String type;
    private String reference;
}
