package com.mcw.moneytransfer.service;

import com.mcw.moneytransfer.exception.InsufficientFundsException;
import com.mcw.moneytransfer.exception.InvalidInputException;
import com.mcw.moneytransfer.model.Account;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private TransferService transferService;
    private Account fromAccount;

    private Account toAccount;

    @BeforeEach
    void setUp() {
        transferService = new TransferService();
        fromAccount = new Account("A1", BigDecimal.valueOf(1000));
        toAccount = new Account("A2", BigDecimal.valueOf(1000));
    }

    @Order(1)
    @Test
    void test_successful_transaction() throws InsufficientFundsException {
        transferService.transfer(fromAccount, toAccount, BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(1500), toAccount.getBalance());
    }

    @Order(2)
    @Test
    void test_successful_concurrent_transactions() {
        int transactions = 100;
        try (ExecutorService executorService = Executors.newFixedThreadPool(transactions)) {
            for (int i=0; i< transactions; i++) {
                executorService.execute(() ->
                        assertDoesNotThrow(() ->
                                transferService.transfer(fromAccount, toAccount, BigDecimal.valueOf(10))));
            }
        }
        assertEquals(BigDecimal.valueOf(0), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(2000), toAccount.getBalance());
    }

    @Order(3)
    @RepeatedTest(100)
    void test_no_deadlock_during_transactions() {
        int transactions = 100;
        try (ExecutorService executorService = Executors.newFixedThreadPool(transactions)) {
            for (int i=0; i< transactions/2; i++) {
                executorService.execute(() ->
                        assertDoesNotThrow(() ->
                                transferService.transfer(fromAccount, toAccount, BigDecimal.valueOf(50))));
                executorService.execute(() ->
                        assertDoesNotThrow(() ->
                                transferService.transfer(toAccount, fromAccount, BigDecimal.valueOf(50))));
            }
        }
        assertEquals(BigDecimal.valueOf(1000), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(1000), toAccount.getBalance());
    }

    @Order(4)
    @Test
    void test_invalid_input_throws_exception() {
        assertEquals(
                "fromAccount cannot be null",
                assertThrowsExactly(
                        InvalidInputException.class,
                        () -> transferService.transfer(null, toAccount, BigDecimal.valueOf(50))).getMessage()
        );

        assertEquals("toAccount cannot be null",
                assertThrowsExactly(
                        InvalidInputException.class,
                        () -> transferService.transfer(fromAccount, null, BigDecimal.valueOf(50))).getMessage()
        );

        assertEquals("From and To account cannot be same",
                assertThrowsExactly(
                        InvalidInputException.class,
                        () -> transferService.transfer(fromAccount, fromAccount, BigDecimal.valueOf(50))).getMessage()
        );

        assertEquals("Amount should be more than Zero",
                assertThrowsExactly(
                        InvalidInputException.class,
                        () -> transferService.transfer(fromAccount, toAccount, BigDecimal.valueOf(0))).getMessage()
        );

        assertEquals("Insufficient balance to complete this transaction",
                assertThrowsExactly(
                        InsufficientFundsException.class,
                        () -> transferService.transfer(fromAccount, toAccount, BigDecimal.valueOf(1100))).getMessage()
        );
    }
}