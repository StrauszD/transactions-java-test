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
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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
        DayOfWeek dayOfFirstTransaction = transactions.get(0).getDate().getDayOfWeek();
        LocalDate weekStart = transactions.get(0).getDate();
        LocalDate lastDayOfTheMonth = transactions.get(0).getDate().with(TemporalAdjusters.lastDayOfMonth());

        if (!dayOfFirstTransaction.equals(DayOfWeek.FRIDAY)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(Date.from(transactions.get(0).getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            cal.set(Calendar.DAY_OF_WEEK, DayOfWeek.FRIDAY.getValue());
            weekStart = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        LocalDate weekEnd = weekStart.plusDays(6);
        TransactionReport transactionReport = new TransactionReport(userId, 0, 0.0, 0.0, weekStart.toString(), weekEnd.toString());

        for (Transaction transaction : transactions) {
            if (transaction.getDate().compareTo(weekStart) >= 0 && transaction.getDate().compareTo(weekEnd) <= 0) {
                transactionReport.setUserId(transaction.getUserId());
                transactionReport.setAmount(transactionReport.getAmount() + transaction.getAmount());
                transactionReport.setQuantity(transactionReport.getQuantity() + 1);
                transactionReport.setStartDate(weekStart.toString());
                transactionReport.setEndDate(weekEnd.toString());
            } else {
                reports.add(transactionReport);
                weekStart = weekEnd.plusDays(1);
                weekEnd = weekStart.plusDays(6);

                if (!weekStart.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                    weekEnd = weekStart.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                }

                if (weekEnd.isAfter(lastDayOfTheMonth)) {
                    weekEnd = lastDayOfTheMonth;
                    lastDayOfTheMonth = weekEnd.plusDays(1).with(TemporalAdjusters.lastDayOfMonth());
                }

                transactionReport = new TransactionReport(
                    userId,
                    1,
                    transaction.getAmount(),
                    transactionReport.getTotalAmount() + transactionReport.getAmount(),
                    weekStart.toString(),
                    weekEnd.toString()
                );
            }
        }

        reports.add(transactionReport);

        return reports;
    }

}
