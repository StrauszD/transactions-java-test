package com.clip.danielstrauszsamplejavatest.configurations;

import com.clip.danielstrauszsamplejavatest.entities.Transaction;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Configuration
public class InitDataConfiguration {
    @Autowired
    Cache<Long, ArrayList> cache;

    @Bean
    public void initData() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-08"), 1L);
        Transaction transaction1 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-09"), 1L);
        Transaction transaction2 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-14"), 1L);
        Transaction transaction3 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-17"), 1L);
        Transaction transaction4 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-17"), 1L);
        Transaction transaction5 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-19"), 1L);
        Transaction transaction6 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-23"), 1L);
        Transaction transaction7 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-25"), 1L);
        Transaction transaction8 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-27"), 1L);
        Transaction transaction9 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-11-30"), 1L);
        Transaction transaction10 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-12-03"), 1L);
        Transaction transaction11 = new Transaction(UUID.randomUUID().toString(), 10.0, "Test", LocalDate.parse("2019-12-06"), 1L);
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        transactions.add(transaction5);
        transactions.add(transaction6);
        transactions.add(transaction7);
        transactions.add(transaction8);
        transactions.add(transaction9);
        transactions.add(transaction10);
        transactions.add(transaction11);
        cache.put(1L, transactions);
    }
}
