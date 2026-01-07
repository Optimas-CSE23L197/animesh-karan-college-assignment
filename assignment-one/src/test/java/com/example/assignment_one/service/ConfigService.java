package com.example.assignment_one.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.assignment_one.model.SectionConfig;

class ConfigServiceImplTest {

    @Test
    void shouldLoadOrderServiceSection() throws Exception {
        // given
        ConfigService service = new ConfigServiceImpl();
        service.loadTxtFile("data/config.txt");

        // when
        SectionConfig section = service.getSection("Order Service");

        // then
        assertNotNull(section);
        assertEquals(
            List.of("test_os_topic_1", "test_os_topic_2"),
            section.toResponse().get("topic")
        );
    }
}
