package com.training.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class Decision {
    private String id;
    private String reference;
    private LocalDate judgmentDate;
    private String level;
    private String nature;
    private String robotSource;
}

