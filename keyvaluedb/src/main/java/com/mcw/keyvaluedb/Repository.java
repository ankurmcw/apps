package com.mcw.keyvaluedb;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Repository<E> {

    private final Map<String, E> database = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void create(String key, E value) {
        database.put(key, value);
    }

    public Optional<E> read(String key) {
        return Optional.ofNullable(database.get(key));
    }

    public E update(String key, E value) {
        try {
            lock.lock();
            if (!database.containsKey(key)) {
                throw new IllegalArgumentException("Key does not exist");
            }
            database.put(key, value);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void delete(String key) {
        try {
            lock.lock();
            database.remove(key);
        } finally {
            lock.unlock();
        }
    }
}
