package com.example.assignment_one.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment_one.model.SectionConfig;
import com.example.assignment_one.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ConfigController {

    @Autowired
    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/load-file")
    public ResponseEntity<String> loadFile(@RequestParam String filePath) {
        try {
            configService.loadTxtFile(filePath);
            return ResponseEntity.ok("Config file loaded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/config")
    public ResponseEntity<?> getSection(@RequestParam String section) {
        SectionConfig config = configService.getSection(section);

        if (config == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Section not found: " + section);

        }

        return ResponseEntity.ok(config.toResponse());
    }
}
