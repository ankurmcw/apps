package model

import "context"

type StateTransition struct {
    currentState *State
    event        *Event
    nextState    *State
}

func NewStateTransition(ctx context.Context, event *Event, currentState, nextState *State) *StateTransition {
    return &StateTransition{
        currentState: currentState,
        event: event,
        nextState: nextState,
    }
}

func (st *StateTransition) GetCurrentState(ctx context.Context) *State {
    return st.currentState
}

func (st *StateTransition) GetEvent(ctx context.Context) *Event {
    return st.event
}

func (st *StateTransition) GetNextState(ctx context.Context) *State {
    return st.nextState
}
