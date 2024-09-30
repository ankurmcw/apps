package com.mcw.kevdb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Database<K, V> {

    private final Map<K, V> store = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void put(K key, V value) {
        store.put(key, value);
    }

    public V get(K key) {
        return store.get(key);
    }

    public void delete(K key) {
        store.remove(key);
    }

    public void clear() {
        store.clear();
    }

    public int size() {
        return store.size();
    }

    ReentrantLock getLock() {
        return lock;
    }
}
