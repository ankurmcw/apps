package model

import "context"

type Event struct {
    id      string
}

func NewEvent(ctx context.Context, id string) *Event {
    return &Event{
        id: id,
    }
}

func (e *Event) GetId(ctx context.Context) string {
    return e.id
}
