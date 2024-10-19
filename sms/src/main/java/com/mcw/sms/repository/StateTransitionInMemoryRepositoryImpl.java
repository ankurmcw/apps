package com.mcw.sms.repository;

import com.mcw.sms.model.StateTransition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class StateTransitionInMemoryRepositoryImpl implements Repository<StateTransition> {

    private static final Map<String, StateTransition> stateTransitionMap = new ConcurrentHashMap<>();
    private static final StateTransitionInMemoryRepositoryImpl instance = new StateTransitionInMemoryRepositoryImpl();

    private StateTransitionInMemoryRepositoryImpl() {}

    public static StateTransitionInMemoryRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public void save(StateTransition transition) {
        stateTransitionMap.put(transition.event().eventId() + transition.from().code(), transition);
    }

    @Override
    public List<StateTransition> findAll() {
        return new ArrayList<>(stateTransitionMap.values());
    }

    @Override
    public Optional<StateTransition> findOne(String id) {
        return Optional.ofNullable(stateTransitionMap.get(id));
    }
}
