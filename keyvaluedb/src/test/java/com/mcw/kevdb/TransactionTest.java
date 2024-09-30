package com.mcw.kevdb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    Database<String, Integer> db;
    @BeforeEach
    void setUp() {
        db = new Database<>();
        db.put("K1", 10);
    }

    @Test
    void verify_successful_transaction() {
        String key = "K1";
        Transaction<String, Integer> t = new Transaction<>(db);

        t.put(key,  t.get(key) + 10);
        t.commit();
        assertEquals(20, db.get(key));
    }

    @Test
    void verify_value_is_not_update_without_a_commit() {
        String key = "K1";
        Transaction<String, Integer> t = new Transaction<>(db);

        t.put(key, t.get(key) + 10);
        assertEquals(10, db.get(key));
    }

    @Test
    void verify_value_is_overwritten_by_other_transaction() {
        String key = "K1";
        Transaction<String, Integer> t1 = new Transaction<>(db);
        Transaction<String, Integer> t2 = new Transaction<>(db);

        t1.put(key, t1.get(key) + 10);
        t2.put(key, t2.get(key) + 10);

        t2.commit();
        t1.commit();
        assertEquals(20, db.get(key));
    }

    @Test
    void verify_value_is_update_by_sequential_transaction() {
        String key = "K1";
        Transaction<String, Integer> t1 = new Transaction<>(db);
        Transaction<String, Integer> t2 = new Transaction<>(db);

        t1.put(key, t1.get(key) + 10);
        t1.commit();

        t2.put(key, t2.get(key) + 10);
        t2.commit();

        assertEquals(30, db.get(key));
    }

    @Test
    void verify_value_is_rolled_back() {
        // arrange
        String key = "K1";
        Transaction<String, Integer> t = new Transaction<>(db);

        // act
        t.put(key, t.get(key) + 10);
        t.rollback();

        // assert
        assertEquals(10, db.get(key));
    }

    @Test
    void verify_updated_value_is_retrieved_during_a_transaction() {
        // arrange
        String key = "K1";
        Transaction<String, Integer> t = new Transaction<>(db);
        // act
        t.put(key, t.get(key) + 10);
        // assert
        assertEquals(20, t.get(key));
        // act
        t.rollback();
        // assert
        assertEquals(10, db.get(key));
    }

    @RepeatedTest(100)
    void verify_no_deadlock_situation() {

        try(ExecutorService service = Executors.newFixedThreadPool(2)) {
            String key = "K1";
            service.execute(() -> {
                Transaction<String, Integer> t = new Transaction<>(db);
                int currentValue = t.get(key);
                t.put(key,  currentValue + 10);
                t.commit();
                assertEquals(currentValue + 10, db.get(key));
            });

            service.execute(() -> {
                Transaction<String, Integer> t = new Transaction<>(db);
                int currentValue = t.get(key);
                t.put(key,  currentValue + 10);
                t.commit();
                assertEquals(currentValue + 10, db.get(key));
            });
        }

    }
}