package com.clip.danielstrauszsamplejavatest.controllers;

import com.clip.danielstrauszsamplejavatest.entities.Transaction;
import com.clip.danielstrauszsamplejavatest.entities.TransactionRequest;
import com.clip.danielstrauszsamplejavatest.services.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/v1/transactions", produces = {(MediaType.APPLICATION_JSON_VALUE)})
public class TransactionsController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping()
    public ResponseEntity addTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            logger.info("Adding new transaction for user {}", transactionRequest.getUserId());
            Transaction transaction = transactionsService.addTransaction(transactionRequest);

            return new ResponseEntity(transaction, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error adding transaction", ex);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{userId}/{transactionId}")
    public ResponseEntity getTransactionByUserAndTransactionId(@PathVariable Long userId, @PathVariable String transactionId) {
        try {
            logger.info("Getting transaction with id {}", transactionId);
            Transaction transaction = transactionsService.getTransactionById(userId, transactionId);

            if (transaction.getId() == null) {
                logger.info("Transaction with id {} not found", transactionId);

                return new ResponseEntity("Transaction not found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(transaction, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting transaction", ex);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity getAllTransactionsByUser(@PathVariable Long userId) {
        try {
            logger.info("Getting transactions for user id {}", userId);
            ArrayList<Transaction> transactions = transactionsService.getAllTransactionsByUser(userId);

            return new ResponseEntity(transactions, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting transaction", ex);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/sum/{userId}")
    public ResponseEntity getTransactionsSumByUser(@PathVariable Long userId) {
        try {
            logger.info("Getting transactions sum for user id {}", userId);

            return new ResponseEntity(transactionsService.getTransactionsSumByUser(userId), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting sum of transactions for user {}", userId, ex);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/report/{userId}")
    public ResponseEntity getTransactionsReport(@PathVariable Long userId) {
        try {
            logger.info("Getting transactions sum for user id {}", userId);

            return new ResponseEntity(transactionsService.getTransactionsReport(userId), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting sum of transactions for user {}", userId, ex);

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
