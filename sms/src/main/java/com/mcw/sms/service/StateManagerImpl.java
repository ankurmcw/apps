package com.mcw.sms.service;

import com.mcw.sms.model.Event;
import com.mcw.sms.model.State;
import com.mcw.sms.model.StateTransition;
import com.mcw.sms.repository.Repository;
import java.util.Optional;

public class StateManagerImpl implements StateManager {

    private Repository<StateTransition> stateTransitionRepository;
    private static StateManagerImpl instance;

    private StateManagerImpl() {}

    public static synchronized StateManagerImpl getInstance(Repository<StateTransition> stateTransitionRepository) {
        if (instance == null) {
            instance = new StateManagerImpl();
            instance.stateTransitionRepository = stateTransitionRepository;
        }
        return instance;
    }

    @Override
    public State getNextState(Event event, State currentState) {
        Optional<StateTransition> stateTransition = stateTransitionRepository.findOne(event.eventId() + currentState.code());
        if (stateTransition.isPresent()) {
            return stateTransition.get().to();
        }
        throw new IllegalStateException(String.format("No state transition found for event %s and state %s",
                event.eventId(), currentState.code()));
    }
}
