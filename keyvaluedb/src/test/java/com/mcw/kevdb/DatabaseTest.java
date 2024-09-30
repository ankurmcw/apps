package com.mcw.kevdb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database<String, Integer> database;
    @BeforeEach
    void beforeTest() {
        database = new Database<>();
        database.put("K1", 1);
        database.put("K2", 1);
    }

    @AfterEach
    void afterTest() {
        database.clear();
    }
    @RepeatedTest(10)
    void test_concurrent_operations() {
        int operations = 10;

        try (ExecutorService service = Executors.newFixedThreadPool(operations)) {
            for (int i=0; i<operations; i++) {
                service.execute(() -> {
                    int val = database.get("K1");
                    database.put("K1", val + 1);
                });
                service.execute(() -> {
                    int val = database.get("K1");
                    database.put("K1", val + 1);
                });
            }
        }
        System.out.println(database.get("K1"));
        assertTrue(database.get("K1") > 1);
    }
}