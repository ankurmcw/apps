package com.mcw.moneytransfer.service;

import com.mcw.moneytransfer.exception.InsufficientFundsException;
import com.mcw.moneytransfer.exception.InvalidInputException;
import com.mcw.moneytransfer.model.Account;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

public class TransferService {

    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) throws InsufficientFundsException {
        if (Objects.isNull(fromAccount)) {
            throw new InvalidInputException("fromAccount cannot be null");
        }

        if (Objects.isNull(toAccount)) {
            throw new InvalidInputException("toAccount cannot be null");
        }

        if (fromAccount.equals(toAccount)) {
            throw new InvalidInputException("From and To account cannot be same");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("Amount should be more than Zero");
        }

        Account firstLock = fromAccount.getAccountId().compareTo(toAccount.getAccountId()) < 0? fromAccount: toAccount;
        Account secondLock = fromAccount.getAccountId().compareTo(toAccount.getAccountId()) < 0? toAccount: fromAccount;

        Lock lock1 = firstLock.getLock();
        Lock lock2 = secondLock.getLock();

        try {
            lock1.lock();
            try {
                lock2.lock();
                if (fromAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientFundsException("Insufficient balance to complete this transaction");
                }
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            } finally{
                lock2.unlock();
            }
        } finally{
            lock1.unlock();
        }
    }
}
