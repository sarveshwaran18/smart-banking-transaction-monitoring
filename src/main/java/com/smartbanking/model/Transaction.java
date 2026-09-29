package com.smartbanking.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private String accountNumber;
    private String type;
    private BigDecimal amount;
    private String description;
    private boolean flagged;
    private String alertReason;
    private Timestamp transactionTime;

    public Transaction(int id, String accountNumber, String type, BigDecimal amount,
                       String description, boolean flagged, String alertReason,
                       Timestamp transactionTime) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.flagged = flagged;
        this.alertReason = alertReason;
        this.transactionTime = transactionTime;
    }

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public String getAlertReason() {
        return alertReason;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }
}
