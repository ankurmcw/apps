package repository

import (
	"log"
	"sms_go/model"
)

type InMemoryStateTransitionRepository struct {
    store map[string]*model.StateTransition
}

func NewInMemoryStateTransitionRepository() *InMemoryStateTransitionRepository {
    return &InMemoryStateTransitionRepository{
        store: make(map[string]*model.StateTransition),
    }
}

func (i *InMemoryStateTransitionRepository) Save(stateTransition *model.StateTransition) error {
    i.store[stateTransition.GetCurrentState().GetId() + stateTransition.GetEvent().GetId()] = stateTransition
    log.Printf("persisted state transition %+v", stateTransition)
    return nil
}

func (i *InMemoryStateTransitionRepository) Find(id string) *model.StateTransition {
    return i.store[id]
}
