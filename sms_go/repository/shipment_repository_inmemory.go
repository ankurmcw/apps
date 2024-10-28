package repository

import (
	"context"
	"sms_go/model"
)

type InMemoryShipmentRepository struct {
    store map[string]*model.Shipment
}

func NewInMemoryShipmentRepository(ctx context.Context) *InMemoryShipmentRepository {
    return &InMemoryShipmentRepository{
        store: make(map[string]*model.Shipment),
    }
}

func (i *InMemoryShipmentRepository) Save(ctx context.Context, shipment *model.Shipment) error {
    i.store[shipment.GetShipmentId(ctx)] = shipment
    return nil
}

func (i *InMemoryShipmentRepository) Find(ctx context.Context, id string) *model.Shipment {
    return i.store[id]
}
