package repository

import (
	"sms_go/model"
)

type ShipmentRepository interface {
    Save(shipment *model.Shipment) error
    Find(id string) *model.Shipment
}

type StateRepository interface {
    Save(state *model.State) error
    Find(id string) *model.State
}

type EventRepository interface {
    Save(state *model.Event) error
    Find(id string) *model.Event
}

type StateTransitionRepository interface {
    Save(state *model.StateTransition) error
    Find(id string) *model.StateTransition
}
