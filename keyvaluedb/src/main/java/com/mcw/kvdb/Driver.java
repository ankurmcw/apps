package com.mcw.kvdb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {

    public static void main(String[] args){
        Database<String, Integer> db = new Database<>();

        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            service.execute(() -> {
                Transaction<String, Integer> t1 = new Transaction<>(db);
                t1.put("K1", 10);
                t1.commit();
            });
            service.execute(() -> {
                Transaction<String, Integer> t2 = new Transaction<>(db);
                t2.put("K1", 20);
                t2.commit();
            });
        }
        System.out.printf("Value of 'K1' is %d%n", db.get("K1"));
    }
}
