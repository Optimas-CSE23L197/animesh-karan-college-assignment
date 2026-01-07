package com.example.assignment_two.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PricingServiceImplTest {

    @Autowired
    private PricingService pricingService;

    @BeforeEach
    void setup() throws Exception {
        // load test TSV before each test
        pricingService.loadTsvFile("data/config.tsv");
    }

    @Test
    void shouldLoadTsvFileAndStoreData() {
        // when
        String price = pricingService.getPrice("u09099000", "10:05");

        // then
        assertEquals("5000", price);
    }

    @Test
    void shouldReturnNotSetForInvalidTime() {
        String price = pricingService.getPrice("u09099000", "10:10");
        assertEquals("NOT SET", price);
    }

    @Test
    void shouldReturnSkuidNotSetIfSkuNotFound() {
        String price = pricingService.getPrice("invalidSku", "10:05");
        assertEquals("SKUID NOT SET", price);
    }
}
