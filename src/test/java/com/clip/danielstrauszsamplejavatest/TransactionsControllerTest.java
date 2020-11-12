package com.clip.danielstrauszsamplejavatest;

import com.clip.danielstrauszsamplejavatest.controllers.TransactionsController;
import com.clip.danielstrauszsamplejavatest.entities.Transaction;
import com.clip.danielstrauszsamplejavatest.entities.TransactionRequest;
import com.clip.danielstrauszsamplejavatest.services.TransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsController.class)
public class TransactionsControllerTest {

    @MockBean
    TransactionsService transactionsService;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTransaction() throws Exception {
        // Given
        TransactionRequest transactionRequest = new TransactionRequest(10.0, "Test", LocalDate.parse("2019-11-08"), 1L);
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-08"), 1L);

        // When
        Mockito.when(transactionsService.addTransaction(transactionRequest)).thenReturn(transaction);


        // Then
        this.mvc.perform(post("/api/v1/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequest)))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void getTransaction() throws Exception {
        // When
        Transaction transaction = new Transaction("asd", 10.0, "Test", LocalDate.parse("2019-11-08"), 1L);
        Mockito.when(transactionsService.getTransactionById(1L, "asd")).thenReturn(transaction);

        // Then
        this.mvc.perform(get("/api/v1/transactions/1/asd"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void getAllTransactions() throws Exception {
        // When
        Transaction transaction = new Transaction("asd", 10.0, "Test", LocalDate.parse("2019-11-08"), 1L);
        ArrayList<Transaction> transactions = new ArrayList<>() {{
            add(transaction);
        }};
        Mockito.when(transactionsService.getAllTransactionsByUser(1L)).thenReturn(transactions);

        // Then
        this.mvc.perform(get("/api/v1/transactions/1"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void getSumOfTransactions() throws Exception {
        // When
        HashMap<String, Object> response = new HashMap<>() {{
            put("user_id", 1);
            put("sum", 22.22);
        }};
        Mockito.when(transactionsService.getTransactionsSumByUser(1L)).thenReturn(response);

        // Then
        this.mvc.perform(get("/api/v1/transactions/sum/1"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void getReport() throws Exception {
        // When
        Mockito.when(transactionsService.getTransactionsReport(1L)).thenReturn(new ArrayList<>());

        // Then
        this.mvc.perform(get("/api/v1/transactions/report/1"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

}
