package com.example.assignment_two.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.assignment_two.service.PricingService;

@RestController
@RequestMapping("/pricing")
@CrossOrigin(origins = "*")
public class PricingController {

    // import price service
    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    // post mapping to load tsv data
    @PostMapping("/load")
    public ResponseEntity<String> loadTsv(@RequestParam String filePath) throws IOException {
        System.out.println("Message from controller layer");
        pricingService.loadTsvFile(filePath);
        return ResponseEntity.ok("TSV file loaded successfully");
    }

    @GetMapping("/price")
    public ResponseEntity<String> getPrice(
            @RequestParam String skuid,
            @RequestParam(required = false) String time) {

        return ResponseEntity.ok(pricingService.getPrice(skuid, time));
    }
}
