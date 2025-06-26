package com.training.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Party {
    private List<String> name;
    private String type;
    private List<String> representatives;
}
