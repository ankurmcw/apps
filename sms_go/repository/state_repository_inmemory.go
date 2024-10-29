package repository

import (
    "log"
	"sms_go/model"
)


type InMemoryStateRepository struct {
    store map[string]*model.State
}

func NewInMemoryStateRepository() *InMemoryStateRepository {
    return &InMemoryStateRepository{
        store: make(map[string]*model.State),
    }
}

func (i *InMemoryStateRepository) Save(state *model.State) error {
    i.store[state.GetId()] = state
    log.Printf("persisted state %+v", state)
    return nil
}

func (i *InMemoryStateRepository) Find(id string) *model.State {
    return i.store[id]
}
