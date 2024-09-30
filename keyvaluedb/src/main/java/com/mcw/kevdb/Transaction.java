package com.mcw.kevdb;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transaction<K, V> {

    private final Map<K, V> originalValues = new HashMap<>();
    private final Database<K, V> db;
    private K key;
    private V value;
    private boolean isActive = true;

    private static final String MESSAGE = "Transaction is not active";

    public Transaction(Database<K, V> db) {
        this.db = db;
    }

    public void put(K key, V value) {
        if (!isActive) throw new IllegalStateException(MESSAGE);
        try {
            db.getLock().lock();
            originalValues.put(key, this.db.get(key));
            this.key = key;
            this.value = value;
        } catch (Exception e){
            db.getLock().unlock();
        }
    }

    public V get(K key) {
        // Isolation level is READ_UNCOMMITTED
        return Objects.isNull(this.value) ? db.get(key): this.value;
    }

    void commit() {
        try {
            db.put(this.key, this.value);
        } finally {
            isActive = false;
            db.getLock().unlock();
        }
    }

    void rollback() {
        try {
            if (!isActive) throw new IllegalStateException(MESSAGE);
            for (Map.Entry<K, V> entry: originalValues.entrySet()) {
                if (Objects.isNull(entry.getValue())) {
                    db.delete(entry.getKey());
                } else {
                    db.put(entry.getKey(), entry.getValue());
                }
            }
            isActive = false;
        } finally {
            isActive = false;
            db.getLock().unlock();
        }
    }
}
