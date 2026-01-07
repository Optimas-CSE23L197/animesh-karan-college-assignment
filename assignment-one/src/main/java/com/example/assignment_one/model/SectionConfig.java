package com.example.assignment_one.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionConfig {

    private final Map<String, String> singleValues = new HashMap<>();
    private final Map<String, List<String>> listValues = new HashMap<>();

    public void put(String key, Object value) {
        if (value instanceof List<?>) {
            listValues.put(key, (List<String>) value);
        } else {
            singleValues.put(key, (String) value);
        }
    }

    public Map<String, Object> toResponse() {
        Map<String, Object> response = new HashMap<>();
        response.putAll(singleValues);
        response.putAll(listValues);
        return response;
    }
}
