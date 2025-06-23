package com.training.controller;

import com.training.service.LeetcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/leetcode")
public class LeetcodeController {

    @Autowired
    private LeetcodeService leetcodeService;

    @GetMapping("/run/{fileName}")
    public ResponseEntity<String> runCode(@PathVariable("fileName") String fileName){

        try {
            Path filePath = Paths.get("src/main/java/com/training/week1/"+ fileName + ".java");
            String code = Files.readString(filePath);

            String output = leetcodeService.submitCode(code);
            return ResponseEntity.ok(output);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found : " + e.getMessage());
        }
    }
}
