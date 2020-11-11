package com.clip.danielstrauszsamplejavatest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class TransactionRequest {
    private Double amount;

    private String description;

    private LocalDate date;

    @JsonProperty("user_id")
    private Long userId;

    public TransactionRequest() {
    }

    public TransactionRequest(Double amount, String description, LocalDate date, Long userId) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void LocalDate(LocalDate date) {
        this.date = date;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
