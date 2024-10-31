package service

import (
	"context"
	"fmt"
	"sms_go/model"
	"sms_go/repository"
)

type StateManager struct {
	stateTransitionRepo repository.StateTransitionRepository
}

func NewStateManager(ctx context.Context, stateTransitionRepo repository.StateTransitionRepository) *StateManager {
	return &StateManager{
		stateTransitionRepo: stateTransitionRepo,
	}
}

func (sm *StateManager) GetNextState(ctx context.Context, currentState *model.State, event *model.Event) (*model.State, error) {
	id := currentState.GetId() + event.GetId()
	stateTransition := sm.stateTransitionRepo.Find(id)
	if stateTransition == nil {
		return nil, fmt.Errorf("no transitions found for state '%s' and event '%s", currentState.GetId(), event.GetId())
	}
	return stateTransition.GetNextState(), nil
}
