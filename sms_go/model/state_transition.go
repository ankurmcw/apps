package model

type StateTransition struct {
	currentState *State
	event        *Event
	nextState    *State
}

func NewStateTransition(event *Event, currentState, nextState *State) *StateTransition {
	return &StateTransition{
		currentState: currentState,
		event:        event,
		nextState:    nextState,
	}
}

func (st *StateTransition) GetCurrentState() *State {
	return st.currentState
}

func (st *StateTransition) GetEvent() *Event {
	return st.event
}

func (st *StateTransition) GetNextState() *State {
	return st.nextState
}
