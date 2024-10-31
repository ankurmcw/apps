package repository

import (
	"log"
	"sms_go/model"
)

type InMemoryEventRepository struct {
	store map[string]*model.Event
}

func NewInMemoryEventRepository() *InMemoryEventRepository {
	return &InMemoryEventRepository{
		store: make(map[string]*model.Event),
	}
}

func (i *InMemoryEventRepository) Save(evet *model.Event) error {
	i.store[evet.GetId()] = evet
	log.Printf("persisted event %+v", evet)
	return nil
}

func (i *InMemoryEventRepository) Find(id string) *model.Event {
	return i.store[id]
}
