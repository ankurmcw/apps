package com.mcw.moneytransfer.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final String accountId;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        try {
            lock.lock();
            return balance;
        } finally{
            lock.unlock();
        }
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getAccountId().equals(account.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId());
    }

    public void withdraw(BigDecimal amount) {
        try {
            lock.lock();
            balance = balance.subtract(amount);
        } finally{
            lock.unlock();
        }
    }

    public void deposit(BigDecimal amount) {
        try {
            lock.lock();
            balance = balance.add(amount);
        } finally{
            lock.unlock();
        }
    }
}
