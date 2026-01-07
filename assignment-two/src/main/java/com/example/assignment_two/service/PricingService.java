package com.example.assignment_two.service;

import java.io.IOException;

public interface PricingService {
    void loadTsvFile(String filePath) throws IOException;

    String getPrice(String skuid, String time);
}
