package com.finalProject.nisha.controllers;

import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class InvoiceControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //invoiceDto = new InvoiceDto();
        //product1 = new Product(1L, "Laptop", 50, "Samsung", null, "jas.png", "image/png", null, null);
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
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/invoice"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


        // @Test
    // void getAllInvoice() {
    // }
    //
    // @Test
    // void getInvoice() {
    // }
}