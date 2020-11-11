package com.clip.danielstrauszsamplejavatest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Transaction {

    @JsonProperty("transaction_id")
    private String id;

    private Double amount;

    private String description;

    private LocalDate date;

    @JsonProperty("user_id")
    private Long userId;

    public Transaction(String id, Double amount, String description, LocalDate date, Long userId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public Transaction() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getUserId() {
        return userId;
    }
}
