package com.example.assignment_two.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.assignment_two.model.PriceWindow;

@Service
public class PricingServiceImple implements PricingService {

    // use ConcurrentHashMap for better access time
    private final Map<String, List<PriceWindow>> priceMap = new ConcurrentHashMap<>();

    // Formatter for parsing 24-hour time values (H:mm)
    private static final DateTimeFormatter TIME_FORMATTER =
        DateTimeFormatter.ofPattern("H:mm");


    @Override
    public void loadTsvFile(String filePath) throws IOException {
        // check file path -> if null or blank throw error
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        // use input stream superclass and load the class also get the raw data
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        System.out.println(inputStream);

        // Throw exception if the resource file cannot be found
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + filePath);
        }

        // Read TSV file contents using BufferedReader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // Skip header line containing column information
            reader.lines().skip(1).forEach(line -> {
                // split line based on pipe symbol
                String[] parts = line.split("\\|");
                String skuId = parts[0].trim();
                System.out.println("SKU=[" + skuId + "]");
                LocalTime starTime = LocalTime.parse(parts[1].trim(), TIME_FORMATTER);
                LocalTime endTime = LocalTime.parse(parts[2].trim(), TIME_FORMATTER);
                int price = Integer.parseInt(parts[3].trim());

                // Create a PriceWindow object from parsed values
                PriceWindow window = new PriceWindow(starTime, endTime, price);

                // Store PriceWindow in map grouped by SKU ID
                priceMap.computeIfAbsent(skuId, k -> new ArrayList<>()).add(window);
            });
        }
    }


    @Override
    public String getPrice(String skuid, String time) {
        // Validate that the SKU ID exists in the price map
        if (!priceMap.containsKey(skuid))
            return "SKUID NOT SET";


        // Validate that the time parameter is provided
        if (time == null || time.isBlank())
            return "TIME NOT SET";

        // Parse query time from input string
        LocalTime queryTime;
        try {
            queryTime = LocalTime.parse(time);
        } catch (Exception e) {
            return "NOT SET";
        }

        // Find matching price window for the given SKU and time
        return priceMap.get(skuid).stream().filter(p -> !queryTime.isBefore(p.getStarTime()) && queryTime.isBefore(p.getEndTime())).reduce((first,second) -> second)
        .map(p -> String.valueOf(p.getPrice()))
                .orElse("NOT SET");
     }
}
