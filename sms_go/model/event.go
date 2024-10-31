package model

type Event struct {
	id string
}

func NewEvent(id string) *Event {
	return &Event{
		id: id,
	}
}

func (e *Event) GetId() string {
	return e.id
}
