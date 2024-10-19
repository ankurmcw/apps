package com.mcw.sms.repository;

import com.mcw.sms.model.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class StateRepositoryInMemoryImpl implements Repository<State> {
    private static final Map<String, State> states = new ConcurrentHashMap<>();
    private static final StateRepositoryInMemoryImpl instance = new StateRepositoryInMemoryImpl();

    private StateRepositoryInMemoryImpl() {}

    public static StateRepositoryInMemoryImpl getInstance() {
        return instance;
    }

    @Override
    public void save(State state) {
        states.put(state.code(), state);
    }

    @Override
    public Optional<State> findOne(String code) {
        return Optional.ofNullable(states.get(code));
    }

    @Override
    public List<State> findAll() {
        return new ArrayList<>(states.values());
    }
}
