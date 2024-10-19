package com.mcw.sms.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    void save(T entity);
    List<T> findAll();
    Optional<T> findOne(String id);
}
