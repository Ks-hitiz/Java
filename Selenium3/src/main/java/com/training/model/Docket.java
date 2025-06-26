package com.training.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Docket {
    private String id;
    private String reference;
    private String courtId;
    private String judge;
}
