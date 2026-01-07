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

    private final Map<String, List<PriceWindow>> priceMap = new ConcurrentHashMap<>();

    private static final DateTimeFormatter TIME_FORMATTER =
        DateTimeFormatter.ofPattern("H:mm");


    @Override
    public void loadTsvFile(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        System.out.println(inputStream);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().skip(1).forEach(line -> {
                String[] parts = line.split("\\|");
                String skuId = parts[0].trim();
                System.out.println("SKU=[" + skuId + "]");
                LocalTime starTime = LocalTime.parse(parts[1].trim(), TIME_FORMATTER);
                LocalTime endTime = LocalTime.parse(parts[2].trim(), TIME_FORMATTER);
                int price = Integer.parseInt(parts[3].trim());

                PriceWindow window = new PriceWindow(starTime, endTime, price);

                priceMap.computeIfAbsent(skuId, k -> new ArrayList<>()).add(window);
            });
        }
    }


    @Override
    public String getPrice(String skuid, String time) {
        if (!priceMap.containsKey(skuid))
            return "SKUID NOT SET";

        if (time == null || time.isBlank())
            return "TIME NOT SET";

        LocalTime queryTime;
        try {
            queryTime = LocalTime.parse(time);
        } catch (Exception e) {
            return "NOT SET";
        }

        return priceMap.get(skuid).stream().filter(p -> !queryTime.isBefore(p.getStarTime()) && queryTime.isBefore(p.getEndTime())).reduce((first,second) -> second)
        .map(p -> String.valueOf(p.getPrice()))
                .orElse("NOT SET");
     }
}
