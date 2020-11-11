package com.clip.danielstrauszsamplejavatest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionReport {
    @JsonProperty("user_id")
    private Long userId;

    private Integer quantity;

    private Double amount;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    public TransactionReport() {
    }

    public TransactionReport(Long userId, Integer quantity, Double amount, Double totalAmount, String startDate, String endDate) {
        this.userId = userId;
        this.quantity = quantity;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
