package model

type Shipment struct {
	id             string
	currentState   *State
	previousStates []*State
}

func (s *Shipment) AddNextState(state *State) {
	s.previousStates = append(s.previousStates, s.currentState)
	s.currentState = state
}

func (s *Shipment) GetCurrentState() *State {
	return s.currentState
}

func (s *Shipment) GetPreviousStates() []*State {
	return s.previousStates
}

func (s *Shipment) GetShipmentId() string {
	return s.id
}

func NewShipment(id string, currentState *State) *Shipment {
	return &Shipment{
		id:           id,
		currentState: currentState,
	}
}
