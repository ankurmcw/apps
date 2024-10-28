package repository

import (
	"context"
	"log"
	"sms_go/model"
)


type InMemoryStateTransitionRepository struct {
    store map[string]*model.StateTransition
}

func NewInMemoryStateTransitionRepository(ctx context.Context) *InMemoryStateTransitionRepository {
    return &InMemoryStateTransitionRepository{
        store: make(map[string]*model.StateTransition),
    }
}

func (i *InMemoryStateTransitionRepository) Save(ctx context.Context, stateTransition *model.StateTransition) error {
    i.store[stateTransition.GetCurrentState(ctx).GetId(ctx) + stateTransition.GetEvent(ctx).GetId(ctx)] = stateTransition
    log.Printf("persisted state transition %+v", stateTransition)
    return nil
}

func (i *InMemoryStateTransitionRepository) Find(ctx context.Context, id string) *model.StateTransition {
    return i.store[id]
}
