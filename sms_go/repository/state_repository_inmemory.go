package repository

import (
	"context"
    "log"
	"sms_go/model"
)


type InMemoryStateRepository struct {
    store map[string]*model.State
}

func NewInMemoryStateRepository(ctx context.Context) *InMemoryStateRepository {
    return &InMemoryStateRepository{
        store: make(map[string]*model.State),
    }
}

func (i *InMemoryStateRepository) Save(ctx context.Context, state *model.State) error {
    i.store[state.GetId(ctx)] = state
    log.Printf("persisted state %+v", state)
    return nil
}

func (i *InMemoryStateRepository) Find(ctx context.Context, id string) *model.State {
    return i.store[id]
}
