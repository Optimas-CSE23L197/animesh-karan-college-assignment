package com.example.assignment_one.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.assignment_one.model.SectionConfig;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final Map<String, SectionConfig> configStore = new ConcurrentHashMap<>();

    @Override
    public void loadTxtFile(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + filePath);
}

        List<String> lines =
                new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .toList();

        String currentSection = null;

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty())
                continue;

            // section name
            if (!line.contains("=")) {
                currentSection = line;
                configStore.putIfAbsent(currentSection, new SectionConfig());
                continue;
            }

            // key value without section
            if (currentSection == null) {
                throw new IllegalStateException("Key-Value found before any section declaration");
            }

            String[] parts = line.split("=", 2);
            String key = parts[0].trim();
            String value = parts[1].trim();

            Object finalValue = value.contains(",") ? Arrays.stream(value.split(",")).map(String::trim).toList()
                    : value;

            configStore.get(currentSection).put(key, finalValue);
        }
    }

    @Override
    public SectionConfig getSection(String sectionName) {
        return configStore.get(sectionName);
    }
}