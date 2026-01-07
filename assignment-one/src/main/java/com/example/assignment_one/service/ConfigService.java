package com.example.assignment_one.service;

import java.io.IOException;


import com.example.assignment_one.model.SectionConfig;

public interface ConfigService {

    /**
     * Loads the txt config file into in-memory store
     */
    void loadTxtFile(String filePath) throws IOException;

    /**
     * Returns section-wise configuration
     */
    SectionConfig getSection(String sectionName);
}
