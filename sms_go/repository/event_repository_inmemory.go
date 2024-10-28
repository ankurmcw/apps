package repository

import (
	"context"
    "log"
	"sms_go/model"
)


type InMemoryEventRepository struct {
    store map[string]*model.Event
}

func NewInMemoryEventRepository(ctx context.Context) *InMemoryEventRepository {
    return &InMemoryEventRepository{
        store: make(map[string]*model.Event),
    }
}

func (i *InMemoryEventRepository) Save(ctx context.Context, evet *model.Event) error {
    i.store[evet.GetId(ctx)] = evet
    log.Printf("persisted event %+v", evet)
    return nil
}

func (i *InMemoryEventRepository) Find(ctx context.Context, id string) *model.Event {
    return i.store[id]
}
