package com.smartbanking.model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private int userId;
    private String accountNumber;
    private BigDecimal balance;

    public Account(int id, int userId, String accountNumber, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
