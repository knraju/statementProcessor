package com.rabo.customer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customer.dto.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatementProcessorResourceTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validTransaction() throws Exception {
        List<Transaction> transactions = Arrays.asList(Transaction.builder()
                .reference(111l)
                .accountNumber("1212")
                .startBalance(new BigDecimal("123"))
                .mutation(new BigDecimal("50"))
                .endBalance(new BigDecimal("173"))
                .description("Sample")
                .build());
        mockMvc.perform(post("/statement/processor")
                .content(mapper.writeValueAsString(transactions))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }
}
