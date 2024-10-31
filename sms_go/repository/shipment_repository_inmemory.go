package repository

import (
	"sms_go/model"
	"sync"
)

type InMemoryShipmentRepository struct {
	store sync.Map
}

func NewInMemoryShipmentRepository() *InMemoryShipmentRepository {
	return &InMemoryShipmentRepository{
		store: sync.Map{},
	}
}

func (i *InMemoryShipmentRepository) Save(shipment *model.Shipment) error {
	i.store.Store(shipment.GetShipmentId(), shipment)
	return nil
}

func (i *InMemoryShipmentRepository) Find(id string) *model.Shipment {
	if val, ok := i.store.Load(id); ok {
		return val.(*model.Shipment)
	}
	return nil
}

func (i *InMemoryShipmentRepository) FindAll() []string {
	var shipmentIds []string
	i.store.Range(func(key, value any) bool {
		shipmentIds = append(shipmentIds, value.(*model.Shipment).GetShipmentId())
		return true
	})
	return shipmentIds
}
