package com.clip.danielstrauszsamplejavatest.services;

import com.clip.danielstrauszsamplejavatest.entities.Transaction;
import com.clip.danielstrauszsamplejavatest.entities.TransactionReport;
import com.clip.danielstrauszsamplejavatest.entities.TransactionRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TransactionsService {

    @Autowired
    Cache<Long, ArrayList> cache;

    @Autowired
    ObjectMapper objectMapper;

    public Transaction addTransaction(TransactionRequest transactionRequest) {
        Long userId = transactionRequest.getUserId();
        Transaction transaction = new Transaction(
            UUID.randomUUID().toString(),
            transactionRequest.getAmount(),
            transactionRequest.getDescription(),
            transactionRequest.getDate(),
            userId
        );

        ArrayList cacheResult = cache.get(userId);

        if (cacheResult == null) {
            ArrayList<Transaction> transactionArray = new ArrayList<>();
            transactionArray.add(transaction);
            cache.put(userId, transactionArray);
        } else {
            ArrayList<Transaction> transactions = objectMapper.convertValue(cacheResult, new TypeReference<ArrayList<Transaction>>(){});
            transactions.add(transaction);
            cache.put(userId, transactions);
        }

        return transaction;

    }

    public Transaction getTransactionById(Long userId, String transactionId) {
        ArrayList cacheResult = cache.get(userId);

        if (cacheResult == null) {
            return new Transaction();
        }

        ArrayList<Transaction> transactions = objectMapper.convertValue(cacheResult, new TypeReference<ArrayList<Transaction>>(){});
        Object[] transactionsFiltered = transactions.stream().filter(transaction -> transaction.getId().equals(transactionId)).toArray();

        if (transactionsFiltered.length > 0) {
            return objectMapper.convertValue(transactionsFiltered[0], Transaction.class);
        }

        return new Transaction();
    }

    public ArrayList<Transaction> getAllTransactionsByUser(Long userId) {
        ArrayList cacheResult  = cache.get(userId);

        if (cacheResult == null) {
            return new ArrayList<>();
        }

        ArrayList<Transaction> transactions = objectMapper.convertValue(cacheResult, new TypeReference<ArrayList<Transaction>>(){});
        transactions.sort(Comparator.comparing(Transaction::getDate));

        return transactions;
    }

    public HashMap<String, Object> getTransactionsSumByUser(Long userId) {
        ArrayList cacheResult = cache.get(userId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("user_id", userId);

        if (cacheResult == null) {
            return new HashMap<>();
        }

        ArrayList<Transaction> transactions = objectMapper.convertValue(cacheResult, new TypeReference<ArrayList<Transaction>>(){});

        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        transactions.forEach(transaction -> {
             sum.updateAndGet(v -> v + transaction.getAmount());
         });
        result.put("sum", sum);

        return result;
    }

    public ArrayList<TransactionReport> getTransactionsReport(Long userId) {
        ArrayList cacheResult  = cache.get(userId);
        ArrayList<TransactionReport> reports = new ArrayList<>();

        if (cacheResult == null) {
            return new ArrayList<>();
        }

        ArrayList<Transaction> transactions = objectMapper.convertValue(cacheResult, new TypeReference<ArrayList<Transaction>>(){});
        transactions.sort(Comparator.comparing(Transaction::getDate));
        LocalDate firstFriday = transactions.get(0).getDate().with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        LocalDate lastDayOfTheMonth = transactions.get(0).getDate().with(TemporalAdjusters.lastDayOfMonth());

        transactions.forEach(transaction -> {

        });

        return reports;
    }

}
