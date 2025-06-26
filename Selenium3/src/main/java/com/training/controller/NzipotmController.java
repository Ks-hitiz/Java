package com.training.controller;


import com.training.model.Binder;
import com.training.service.NzipotmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@RestController
@RequestMapping("/api")
public class NzipotmController {

    @Autowired
    private NzipotmService nzipotmService;

    @GetMapping("/run-robot")
    public ResponseEntity<Binder> runRobot(){
        Binder binder = nzipotmService.runComplaintsRobot();
        return ResponseEntity.ok(binder);
    }

    @GetMapping("/nz-complaints/latest")
    public ResponseEntity<String> getLatestBinderJson() throws IOException {
        Path latestJson = Files.list(Paths.get("."))
                .filter(f -> f.toString().endsWith(".json"))
                .sorted(Comparator.comparingLong((Path f) -> f.toFile().lastModified()).reversed())
                .findFirst()
                .orElse(null);

        if (latestJson != null) {
            String content = Files.readString(latestJson);
            return ResponseEntity.ok(content);
        }
        return ResponseEntity.notFound().build();
    }

}
