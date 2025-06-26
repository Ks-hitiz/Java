package com.training.model;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@Component
public class Binder {
    private String id;
    private List<String> domains;
    private String firstAction;
    private String firstActionDate;

    private List<Docket> dockets;
    private List<Party> parties;
    private List<Right> rights;
}

