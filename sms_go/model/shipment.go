package model

import "context"

type Shipment struct {
    id              string
    currentState    *State
    previousStates  []*State
}

func (s *Shipment) AddNextState(ctx context.Context, state *State) {
    s.previousStates = append(s.previousStates, s.currentState)
    s.currentState = state
}

func (s *Shipment) GetCurrentState(ctx context.Context) *State {
    return s.currentState
}

func (s *Shipment) GetPreviousStates(ctx context.Context) []*State {
    return s.previousStates
}

func (s *Shipment) GetShipmentId(ctx context.Context) string {
    return s.id
}

func NewShipment(ctx context.Context, id string, currentState *State) *Shipment {
    return &Shipment{
        id: id,
        currentState: currentState,
    }
}
