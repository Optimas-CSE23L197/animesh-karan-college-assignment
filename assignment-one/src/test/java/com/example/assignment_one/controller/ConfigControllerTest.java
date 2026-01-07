package com.example.assignment_one.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.assignment_one.model.SectionConfig;
import com.example.assignment_one.service.ConfigService;

@WebMvcTest(ConfigController.class)
class ConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @Test
    void shouldReturnOrderServiceConfig() throws Exception {
        // given
        SectionConfig config = new SectionConfig();
        config.put("broker", "https://orbroker.in");
        config.put("topic", List.of("test_os_topic_1", "test_os_topic_2"));

        when(configService.getSection("Order Service"))
                .thenReturn(config);

        // when + then
        mockMvc.perform(get("/api/v1/config")
                .param("section", "Order Service"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.broker").value("https://orbroker.in"))
            .andExpect(jsonPath("$.topic").isArray())
            .andExpect(jsonPath("$.topic[0]").value("test_os_topic_1"));
    }
}
