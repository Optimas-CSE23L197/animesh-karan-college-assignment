package com.example.assignment_two.service;

import java.io.IOException;

public interface PricingService {
    // this will load tsv file
    void loadTsvFile(String filePath) throws IOException;

    // getPrice found price form inmemory data
    String getPrice(String skuid, String time);
}
