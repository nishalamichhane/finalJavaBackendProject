package com.finalProject.nisha.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class InvoiceControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addInvoice() throws Exception {
        String requestJson = """
                                { "invoiceDate" : "2023-07-17T08:14:00",
                                 "totalAmount" : 4000,
                                 "order" : {
                                     "id" : 1

                                 }}

                                                 """;
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void getInvoice() throws Exception {
        String requestJson = """
                                { "invoiceDate" : "2023-06-18T08:14:00",
                                 "totalAmount" : 2626,
                                 "order" : {
                                     "id" : 123

                                 }}""";
        MockMvcRequestBuilders.post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/invoice"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

    }
}