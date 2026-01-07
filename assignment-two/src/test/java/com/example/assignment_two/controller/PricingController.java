package com.example.assignment_two.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.assignment_two.service.PricingService;

@WebMvcTest(PricingController.class)
class PricingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingService pricingService;

    @Test
    void loadEndpointShouldCallService() throws Exception {

        mockMvc.perform(
                post("/pricing/load")
                        .param("filePath", "data/config.tsv"))
                .andExpect(status().isOk());

        // Verify service method was called
        verify(pricingService).loadTsvFile("data/config.tsv");
    }
}
