package repository

import (
	"context"
	"sms_go/model"
)

type ShipmentRepository interface {
    Save(ctx context.Context, shipment *model.Shipment) error
    Find(ctx context.Context, id string) *model.Shipment
}

type StateRepository interface {
    Save(ctx context.Context, state *model.State) error
    Find(ctx context.Context, id string) *model.State
}

type EventRepository interface {
    Save(ctx context.Context, state *model.Event) error
    Find(ctx context.Context, id string) *model.Event
}

type StateTransitionRepository interface {
    Save(ctx context.Context, state *model.StateTransition) error
    Find(ctx context.Context, id string) *model.StateTransition
}
