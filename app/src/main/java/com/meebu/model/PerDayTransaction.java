package com.meebu.model;

/**
 * Created by eleganz on 20/3/19.
 */

public class PerDayTransaction {
    String time,balance,amount;

    public PerDayTransaction(String time, String balance, String amount) {
        this.time = time;
        this.balance = balance;
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
