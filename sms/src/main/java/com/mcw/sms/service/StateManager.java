package com.mcw.sms.service;

import com.mcw.sms.model.Event;
import com.mcw.sms.model.State;

public interface StateManager {
    State getNextState(Event event, State currentState);
}
