package com.training.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Classification {
    private String name;
    private String type;
    private String className;
    private List<String> images;
}
