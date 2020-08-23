package com.rabo.customer.rest;

import com.rabo.customer.handler.ResultCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${success_transaction}")
    private String successTransaction;

    @Value("${duplicate_references_transactions}")
    private String duplicateReferenceTransactions;

    @Value("${incorrect_end_balance_transactions}")
    private String incorrectEndBalanceTransactions;

    @Value("${duplicate_incorrect_transactions}")
    private String duplicateIncorrectBalanceTransactions;

    @Value("${invalid_transaction}")
    private String invalidTransaction;

    private static final String urlTemplate = "/statement/processor";

    @Test
    public void successTransaction() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(successTransaction)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(ResultCodes.SUCCESSFUL.toString())));
    }

    @Test
    public void duplicateRefecesTransaction() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(duplicateReferenceTransactions)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(ResultCodes.DUPLICATE_REFERENCE.toString())));
    }

    @Test
    public void inCorrectEndBalanceTransaction() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(incorrectEndBalanceTransactions)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(ResultCodes.INCORRECT_END_BALANCE.toString())));
    }

    @Test
    public void duplicateInCorrectEndBalanceTransaction() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(duplicateIncorrectBalanceTransactions)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(ResultCodes.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString())));
    }

    @Test
    public void badInvalidTransaction() throws Exception {
        mockMvc.perform(post(urlTemplate)
                .content(invalidTransaction)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(ResultCodes.BAD_REQUEST.toString())));
    }
}
